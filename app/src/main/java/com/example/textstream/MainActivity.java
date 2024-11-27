package com.example.textstream;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LogoutDialogFragment.LogoutDialogListener {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private BlockAdapter blockAdapter;
    private List<BlockItem> blockList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        initUI();

        // Initialize block list data
        initBlockList();

        // Set up RecyclerView with BlockAdapter
        setupRecyclerView();

        // Set up SearchView to filter block list
        setupSearchView();

        // Set up button listeners
        setupButtonListeners();
    }

    /**
     * Initialize UI components.
     */
    private void initUI() {
        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerView);
    }

    /**
     * Initialize the block list with data.
     */
    private void initBlockList() {
        blockList = new ArrayList<>();
        blockList.add(new BlockItem("Operating Systems", "Open Text Book", subjects.class, getString(R.string.os_obj), "os_notes", getString(R.string.os_book)));
        blockList.add(new BlockItem("Computer Networks", "Open Text Book", subjects.class, getString(R.string.cn_obj), "cn_notes", getString(R.string.cn_book)));
        blockList.add(new BlockItem("Graph Theory", "Open Text Book", subjects.class, getString(R.string.gt_obj), "gt_notes", getString(R.string.gt_book)));
        blockList.add(new BlockItem("Web Technologies", "Open Text Book", subjects.class, getString(R.string.wt_obj), "wt_notes", getString(R.string.wt_book)));
        blockList.add(new BlockItem("Compiler Engineering", "Open Text Book", subjects.class, getString(R.string.ce_obj), "ce_notes", getString(R.string.ce_book)));
        blockList.add(new BlockItem("Advances in Databases", "Open Text Book", subjects.class, getString(R.string.adb_obj), "adb_notes", getString(R.string.adb_book)));
        blockList.add(new BlockItem("Digital Logic and Design", "Open Text Book", subjects.class, getString(R.string.dld_obj), "dld_notes", getString(R.string.dld_book)));
        blockList.add(new BlockItem("Programming and Data Structures", "Open Text Book", subjects.class, getString(R.string.pds_obj), "pds_notes", getString(R.string.pds_book)));
        blockList.add(new BlockItem("Database Management Systems", "Open Text Book", subjects.class, getString(R.string.dbms_obj), "dbms_notes", getString(R.string.dbms_book)));
        blockList.add(new BlockItem("OOPS and ADS", "Open Text Book", subjects.class, getString(R.string.oops_obj), "oops_notes", getString(R.string.oops_book)));
    }

    /**
     * Set up RecyclerView with BlockAdapter.
     */
    private void setupRecyclerView() {
        blockAdapter = new BlockAdapter(blockList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(blockAdapter);
    }

    /**
     * Set up SearchView to filter block list based on user input.
     */
    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                blockAdapter.filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                blockAdapter.filter(newText);
                return false;
            }
        });
    }

    /**
     * Set up button listeners for CGPA Calculator, Reminder, and Logout.
     */

    private void setupButtonListeners() {
        Button cgpaCalculatorButton = findViewById(R.id.cgpaCalculator);
        cgpaCalculatorButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CgpaCalculator.class);
            startActivity(intent);
        });

        Button setReminderButton = findViewById(R.id.setReminderButton);
        setReminderButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ReminderActivity.class);
            startActivity(intent);
        });

        ImageView logoutIcon = findViewById(R.id.logoutIcon);
        logoutIcon.setOnClickListener(v -> showLogoutDialog());
    }

    private void showLogoutDialog() {
        LogoutDialogFragment dialog = LogoutDialogFragment.newInstance();
        dialog.show(getSupportFragmentManager(), "LogoutDialog");
    }

    @Override
    public void onLogoutConfirmed() {
        // Clear user session data
        SharedPreferences preferences = getSharedPreferences("UserSession", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isLoggedIn", false); // Mark as logged out
        editor.apply();

        // Redirect to LoginActivity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Close MainActivity
    }
}