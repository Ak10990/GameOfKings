package com.android.thoughtworks.helpers;

import android.text.TextUtils;

import com.android.thoughtworks.App;
import com.android.thoughtworks.model.King;
import com.android.thoughtworks.model.KingDb;

import java.util.ArrayList;

import io.realm.RealmResults;

public class KingModelHelper {

    /**
     * @return search results by name.
     */
    public static ArrayList<King> getKingsBySearchName(String query) {
        if (TextUtils.isEmpty(query)) {
            return null;
        }
        RealmResults<KingDb> list = App.getRealm()
                .where(KingDb.class).contains("name", query, false)
                .findAll();
        ArrayList<King> kings = new ArrayList<>();
        for (KingDb kingDb : list) {
            kings.add(new King().getKingFromDb(kingDb));
        }
        return kings;
    }

    /**
     * @return search results by battle type
     */
    public static ArrayList<King> getKingsBySearchType(String query) {
        RealmResults<KingDb> list = App.getRealm()
                .where(KingDb.class).equalTo("strength", query)
                .findAll();
        ArrayList<King> kings = new ArrayList<>();
        for (KingDb kingDb : list) {
            kings.add(new King().getKingFromDb(kingDb));
        }
        return kings;
    }

    public static ArrayList<King> getKingsBySortQuery(int type) {
        ArrayList<King> kings = new ArrayList<>();
        RealmResults<KingDb> list = App.getRealm().allObjects(KingDb.class);
        if (type == 1) {
            list.sort("name", RealmResults.SORT_ORDER_ASCENDING);
        } else if (type == 2) {
            list.sort("name", RealmResults.SORT_ORDER_DESCENDING);
        }
        King king;
        for (KingDb kingDb : list) {
            king = new King();
            king.getKingFromDb(kingDb);
            kings.add(king);
        }
        return kings;
    }

}