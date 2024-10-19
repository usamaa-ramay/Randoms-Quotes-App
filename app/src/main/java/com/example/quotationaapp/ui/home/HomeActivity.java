package com.example.quotationaapp.ui.home;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.quotationaapp.R;
import com.example.quotationaapp.data.model.QuotesData;
import com.example.quotationaapp.data.utils.BaseResponse;
import com.example.quotationaapp.databinding.ActivityHomeBinding;

import com.example.quotationaapp.ui.favorite.FavoriteActivity;
import com.example.quotationaapp.ui.home.model.HomeModelView;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private String getData;
    private boolean isFavorite = true;
    private HomeModelView homeModelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        homeModelView = new ViewModelProvider(this).get(HomeModelView.class);

        homeModelView._qoute.observe(this, BaseResponse -> {
            switch (BaseResponse.getStatus()) {
                case ONLOADING:
                    break;
                case ONSUCCESS:
                    if (BaseResponse.getData() != null) {
                        binding.quotesTV.setText(BaseResponse.getData().getQuote());
                        binding.authorTV.setText(BaseResponse.getData().getAuthor());
                    }
                    break;
                case ONERROR:
                    Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show();
                    break;
            }
        });


        binding.textFieldCL.setOnClickListener(view -> {
            homeModelView.setQoute();
        });

        binding.shearIconIV.setOnClickListener(view -> {
            String getData = binding.quotesTV.getText().toString();
            if (!getData.isEmpty()) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, getData);
                startActivity(Intent.createChooser(shareIntent, "Share Quote via"));
            } else Log.d("Share", "No quote available to share");
        });

        binding.favoriteIconIV.setOnClickListener(view -> {
            if (!isFavorite) {
                binding.favoriteIconIV.setImageResource(R.drawable.icons);
                isFavorite = true;
            } else {
                binding.favoriteIconIV.setImageResource(R.drawable.filled_icons);
                isFavorite = false;
                startActivity(new Intent(HomeActivity.this, FavoriteActivity.class));
            }

        });

        binding.copyIconIV.setOnClickListener(view -> {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            String quotes = binding.quotesTV.getText().toString();
            String author = binding.authorTV.getText().toString();

            String combinedText = quotes + "\n- " + author;

            ClipData data = ClipData.newPlainText("Copied Text", combinedText);
            clipboard.setPrimaryClip(data);
            Toast.makeText(this, "Text copied", Toast.LENGTH_SHORT).show();

        });
    }
}
