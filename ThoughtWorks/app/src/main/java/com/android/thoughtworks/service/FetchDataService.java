package com.android.thoughtworks.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.android.thoughtworks.App;
import com.android.thoughtworks.helpers.api.BattleApiClient;
import com.android.thoughtworks.helpers.api.BattleApiService;
import com.android.thoughtworks.model.Battle;
import com.android.thoughtworks.model.King;
import com.android.thoughtworks.model.KingDb;
import com.android.thoughtworks.utils.AlertToastUtils;
import com.android.thoughtworks.utils.Constants;
import com.android.thoughtworks.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchDataService extends IntentService {

    public FetchDataService() {
        super(FetchDataService.class.getSimpleName());
    }

    public FetchDataService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        loadBattlesFromApi();
    }

    /**
     * Loading form API
     */
    private void loadBattlesFromApi() {
        if (NetworkUtils.isInternetConnected(this)) {
            BattleApiClient apiClient = new BattleApiClient();
            BattleApiService apiService = apiClient.getApiService();
            final Call<List<Battle>> call = apiService.listBattles();
            if (call != null) {
                call.enqueue(new Callback<List<Battle>>() {
                    @Override
                    public void onResponse(Call<List<Battle>> call, Response<List<Battle>> response) {
                        List<Battle> battles = response.body();
                        if (battles.size() > 0) {
                            ArrayList<King> kings = calculateKing(battles);
                            feedIntoDb(kings);
                            onResult(kings);
                        } else {
                            onError("No Results Found");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Battle>> call, Throwable t) {
                        onError(t.getMessage());
                    }
                });
            }
        } else {
            AlertToastUtils.stopProgressDialog();
        }
    }

    /**
     * Feeding form Db
     */
    private void feedIntoDb(List<King> kings) {
        Realm realm = App.getRealm();
        ArrayList<KingDb> list = new ArrayList<>();
        for (King king : kings) {
            list.add(king.getKingDb());
        }

        realm.beginTransaction();
        realm.copyToRealm(list);
        realm.commitTransaction();

        App.getprefs().edit().putBoolean(Constants.IS_FIRST_LOAD, true).apply();
    }

    private ArrayList<King> calculateKing(List<Battle> battles) {
        int random = -1;
        ArrayList<King> kings = new ArrayList<>();
        Set<String> battleTypesSet = new HashSet<>();
        int color;

        HashMap<String, Integer> nameMap = new HashMap<>();
        int kingsCount = -1;
        King king1;
        King king2;

        for (Battle battle : battles) {
            battleTypesSet.add(battle.getBattle_type());

            String kingsName = battle.getAttacker_king();
            boolean isKingExists = nameMap.containsKey(kingsName);
            int isWin = battle.getAttacker_outcome().equals("draw") ? 0 : -1;
            if (isWin != 0) {
                isWin = (battle.getAttacker_outcome().equals("win")) ? 1 : 2;
            }
            if (isKingExists) {
                king1 = updateKing(kings.get(nameMap.get(kingsName)), battle.getBattle_number(), battle.getBattle_type());
            } else {
                kingsCount++;
                random = (++random % 10);
                color = Constants.color[random];
                king1 = createKing(kingsCount, kingsName, battle.getBattle_number(), battle.getBattle_type(), color);
                kings.add(king1);
                nameMap.put(kingsName, kingsCount);
            }
            king1 = updatePoints(king1, true, isWin);

            kingsName = battle.getDefender_king();
            isKingExists = nameMap.containsKey(kingsName);
            if (isWin != 0) {
                isWin = battle.getAttacker_outcome().equals("loss") ? 1 : 2;
            }
            if (isKingExists) {
                king2 = updateKing(kings.get(nameMap.get(kingsName)), battle.getBattle_number(), battle.getBattle_type());
            } else {
                kingsCount++;
                random = (++random % 10);
                color = Constants.color[random];
                king2 = createKing(kingsCount, kingsName, battle.getBattle_number(), battle.getBattle_type(), color);
                kings.add(king2);
                nameMap.put(kingsName, kingsCount);
            }
            king2 = updatePoints(king2, false, isWin);

            double newRatingK1 = Math.pow(10, (king1.getRating() / Constants.INITIAL_RATING));
            double newRatingK2 = Math.pow(10, (king2.getRating() / Constants.INITIAL_RATING));

            double expectedScore = newRatingK1 / (newRatingK1 + newRatingK2);
            double actualScore = isWin == 0 ? 0.5 : (isWin == 1 ? 0 : 1);
            king1.setRating(king1.getRating() + Constants.K_FACTOR * (actualScore - expectedScore));

            expectedScore = newRatingK2 / (newRatingK1 + newRatingK2);
            actualScore = isWin == 0 ? 0.5 : (isWin == 1 ? 1 : 0);
            king2.setRating(king2.getRating() + Constants.K_FACTOR * (actualScore - expectedScore));

            kings.set(nameMap.get(king1.getName()), king1);
            kings.set(nameMap.get(king2.getName()), king2);

        }
        return kings;
    }

    /**
     * @return New King
     */
    private King createKing(int id, String name, int battleId, String battleType, int color) {
        King king = new King();
        king.setId(id);
        king.setName(name);
        king.setBattleNum(battleId);
        king.setBattleType(battleType);
        king.setColor(color);
        return king;
    }

    /**
     * @return Update King
     */
    private King updateKing(King king, int battleId, String battleType) {
        king.setBattleNum(battleId);
        king.setBattleType(battleType);
        return king;
    }

    /**
     * @return Update King
     */
    private King updatePoints(King king, boolean isAttack, int isWin) {
        if (isAttack) {
            if (isWin == 0) {
                king.addToDrawAttack();
            } else if (isWin == 1) {
                king.addToWinAttack();
            } else {
                king.addToLostAttack();
            }
        } else {
            if (isWin == 0) {
                king.addToDrawDefense();
            } else if (isWin == 1) {
                king.addToWinDefense();
            } else {
                king.addToLostDefense();
            }
        }
        String strength;
        if (king.getAttackWinTotal() > king.getDefenseWinTotal()) {
            strength = Constants.ATTACK;
        } else {
            strength = Constants.DEFENSE;
        }
        king.setStrength(strength);
        return king;
    }


    /**
     * Broadcasting the result
     */
    private void onResult(ArrayList<King> kings) {
        Intent intent = new Intent(Constants.FETCHED_API_SUCCESS);
        intent.putParcelableArrayListExtra(Constants.FETCHED_API_RESULT, kings);
        LocalBroadcastManager.getInstance(FetchDataService.this)
                .sendBroadcast(intent);
    }

    private void onError(String errMessage) {
        Intent intent = new Intent(Constants.FETCHED_API_FAILURE);
        intent.putExtra(Constants.FETCHED_API_ERROR, errMessage);
        LocalBroadcastManager.getInstance(FetchDataService.this)
                .sendBroadcast(intent);
    }
}