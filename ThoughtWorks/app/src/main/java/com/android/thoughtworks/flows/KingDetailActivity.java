package com.android.thoughtworks.flows;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.thoughtworks.R;
import com.android.thoughtworks.model.King;
import com.android.thoughtworks.utils.UIUtils;

/**
 * Created by akanksha on 8/1/17.
 */
public class KingDetailActivity extends AppCompatActivity {

    public static final String KING_EXTRA = "KING_EXTRA";
    private King king = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_king_detail);
        initValues();
        initViews();
    }

    private void initValues() {
        if (getIntent().hasExtra(KING_EXTRA)) {
            king = getIntent().getExtras().getParcelable(KING_EXTRA);
        }
    }

    private void initViews() {
        ImageView imageView = (ImageView) findViewById(R.id.item_image);
        TextView tvName = (TextView) findViewById(R.id.item_name);
        TextView tvRating = (TextView) findViewById(R.id.item_rating);
        TextView tvWon = (TextView) findViewById(R.id.item_won);
        TextView tvLost = (TextView) findViewById(R.id.item_lost);
        TextView tvStrength = (TextView) findViewById(R.id.item_strength);
        TextView tvStrengthType = (TextView) findViewById(R.id.item_strength_type);

        if (king != null) {
            tvName.setText(king.getName());
            tvRating.setText("Highest Rating : " + ((int) king.getRating()));
            tvWon.setText("Battles won : " + (king.getAttackWinTotal() + king.getDefenseWinTotal()));
            tvLost.setText("Battles lost : " + (king.getAttackLostTotal() + king.getDefenseLostTotal()));

            tvStrength.setText("Strength : " + king.getStrength());
            tvStrengthType.setText("Strength in Battle Type : " + king.getBattleType());

            imageView.setImageDrawable(UIUtils.tintImage(ContextCompat.getDrawable(this, R.drawable.king_crown), king.getColor()));
        }

    }

}
