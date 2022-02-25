package com.example.simpletodo;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.drawable.AnimationDrawable;

/*
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
*/

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Objects;

public class Application extends AppCompatActivity {
    public static String KEY_ITEM_TEXT = "item_text";
    public static String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 20;
    private GoogleSignInClient mGoogleSignInClient;

    List<TodoItem> items;

    ImageButton email;
    ImageButton logout;
    ImageButton imageButton;
    EditText editText;
    TextView textView2;
    Button btnAdd;
    EditText edItem;
    RecyclerView rvItem;
    ItemsAdapter itemsAdapter;
    Toolbar toolbar;
    AnimationDrawable animDrawable;

    /*
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    */

    /*
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        Objects.requireNonNull(getSupportActionBar()).hide();

        final LottieAnimationView animationView = findViewById(R.id.animation);

        btnAdd = findViewById(R.id.btnAdd);
        email = findViewById(R.id.email);
        logout = findViewById(R.id.logout);
        toolbar = findViewById(R.id.toolbar);


        /*
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(Application.this, SignInFirebase.class));
                }
            }
        };
        */

        animDrawable = (AnimationDrawable) btnAdd.getBackground();
        animDrawable.setEnterFadeDuration(3);
        animDrawable.setExitFadeDuration(3000);
        animDrawable.start();

        animDrawable = (AnimationDrawable) toolbar.getBackground();
        animDrawable.setEnterFadeDuration(3);
        animDrawable.setExitFadeDuration(3000);
        animDrawable.start();

        items = new ArrayList<TodoItem>();
        //imageButton = findViewById(R.id.imageButton);
        //editText = findViewById(R.id.editText);
        edItem = findViewById(R.id.edItem);
        rvItem = findViewById(R.id.rvItem);
        textView2 = findViewById(R.id.textView2);

        //loadItems();

//        int recyclerNum = items.size() ;
//        String str = String.valueOf(recyclerNum);
        textView2.setText("0");

        /*
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
            }
        });
        */


        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String subject = "Orbital Note";
                String message = "Notes";
                for(int i = 0; i <= items.size() - 1; i++){
                    message += "\n\n" + items.get(i).getItem() ;
                }

                sendMail(subject, message);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });

        ItemsAdapter.OnCheckBoxClickListener checkBoxClickListener = new ItemsAdapter.OnCheckBoxClickListener() {
            @Override
            public void onCheckBoxCLicked(int position) {
                //items.remove(position);
                //itemsAdapter.notifyItemRemoved(position);

                animationView.setVisibility(View.VISIBLE);
                animationView.playAnimation();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        animationView.setVisibility(View.GONE);
                    }
                }, 4000);

            }
        };

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
                items.remove(position);
                itemsAdapter.notifyItemRemoved(position);

                int recyclerNum = items.size() ;
                String str = String.valueOf(recyclerNum);
                textView2.setText(str);

                Toast.makeText(getApplicationContext(),"Item was removed", Toast.LENGTH_SHORT).show();
                //saveItems();
            }
        };

        ItemsAdapter.OnClickListener onClickListener = new ItemsAdapter.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                Log.d("Application", "Single click at position" + position );

                /*
                Intent i = new Intent(Application.this, EditActivity.class);
                i.putExtra(KEY_ITEM_TEXT, items.get(position));
                i.putExtra(KEY_ITEM_POSITION, position);

                startActivityForResult(i, EDIT_TEXT_CODE);
                */
                /*
                EditAct dialog = new EditAct();
                dialog.show(getSupportFragmentManager(), "Test Custom Dialog");
                */

                Dialog dialog = new Dialog(Application.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.activity_edit);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                Button btnSave = dialog.findViewById(R.id.btnSave);
                EditText etItem = dialog.findViewById(R.id.etItem);
                etItem.setText(items.get(position).getItem());

                animDrawable = (AnimationDrawable) btnSave.getBackground();
                animDrawable.setEnterFadeDuration(3);
                animDrawable.setExitFadeDuration(3000);
                animDrawable.start();

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        items.get(position).setItem(etItem.getText().toString());
                        //items.set(position, );
                        itemsAdapter.notifyItemChanged(position);
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        };

        itemsAdapter = new ItemsAdapter(items, onLongClickListener, onClickListener, checkBoxClickListener);
        rvItem.setAdapter(itemsAdapter);
        rvItem.setLayoutManager(new LinearLayoutManager(this));


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = edItem.getText().toString();
                TodoItem item = new TodoItem(todoItem, false);
                items.add(item);
                itemsAdapter.notifyItemInserted(items.size() - 1 );
                edItem.setText("");
                hideKeyboard(Application.this);

                int recyclerNum = items.size() ;
                String str = String.valueOf(recyclerNum);
                textView2.setText(str);

                //Toast.makeText(getApplicationContext(),"Item was added", Toast.LENGTH_SHORT).show();
                //saveItems();
            }
        });

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setQueryHint("Search");
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //String search = editText.getText().toString();
//
//                if(items.contains(search)){
//
//                   int index = items.indexOf(search);
//                    String firstIndex = items.get(0);
//                   items.set(0, search);
//                    itemsAdapter.notifyItemChanged(0, search);
//                    items.set(index, firstIndex);
//                    itemsAdapter.notifyItemChanged(index, firstIndex);
//                    editText.setText("");
//
//
//                   int eleIndex = items.indexOf(search);
//
//                    for(int in = eleIndex; in > 0; in--){
//                     String elementPri = items.get(index - 1).getItem();
//
//                       items.get(index - 1).setItem(search);
//                       //items.set(index - 1, search);
//                        //itemsAdapter.notifyItemChanged(index - 1, search);
//                        items.get(index).setItem(elementPri);
//                        //items.set(index, elementPri);
//                        //itemsAdapter.notifyItemChanged(index, elementPri);
//
//                       itemsAdapter.notifyItemMoved(index , index - 1);
//                       editText.setText("");
//                   }
//
//                }
//            }
//
//
//        });
    }


    private void filterList(String newText) {
        List<TodoItem> filtered = new ArrayList<>();
        for(TodoItem item : items){
            if(item.getItem().toLowerCase().contains(newText.toLowerCase())){
                filtered.add(item);
            }

            itemsAdapter.setFilteredList(filtered);

        }
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void sendMail(String subject, String message){
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        String recipient = acct.getEmail();

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {recipient});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);

        try{
            startActivity(Intent.createChooser(emailIntent, "Choose an Email Client"));
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void signOut() {
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Application.this, "Signed out", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode == EDIT_TEXT_CODE){

            String itemText = data.getStringExtra(KEY_ITEM_TEXT);
            int position = data.getExtras().getInt(KEY_ITEM_POSITION);
            items.get(position).setItem(itemText);
            //items.set(position, itemText);
            itemsAdapter.notifyItemChanged(position);

            //saveItems();
            Toast.makeText(getApplicationContext(), "Items updated successfully", Toast.LENGTH_SHORT).show();
        }else{
            Log.w("Application", "UnKnown call to onActivityResult");
        }
    }

    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

//    private void loadItems(){
//        try{
//            items = new ArrayList<TodoItem>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
//        }catch (IOException e){
//            Log.e("Application", "Error reading items", e);
//            items = new ArrayList<>();
//
//        }
//
//    }

    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("Application", "Error writing items", e);
        }
    }

}