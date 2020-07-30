package com.example.quizzer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuestionsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView que,scr;
    private Button opta,optb,optc,optd,share,next;
    private LinearLayout layout;
    private FloatingActionButton bookmark;
    private int count = 0,pos,score=0,no_ques=0;
    private List<QuestionModel> list;
    private String category;
    private int setNo;


    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        toolbar = findViewById(R.id.tool1);
        setSupportActionBar(toolbar);
        que = findViewById(R.id.question);
        scr = findViewById(R.id.score);
        share = findViewById(R.id.share);
        next = findViewById(R.id.next);
        layout = findViewById(R.id.linearLayout21);
        opta = findViewById(R.id.optA);
        optb = findViewById(R.id.optB);
        optc = findViewById(R.id.optC);
        optd = findViewById(R.id.optD);
        bookmark = findViewById(R.id.bookmarks);
        category = getIntent().getStringExtra("category");
        setNo = getIntent().getIntExtra("setNo",1);


        list = new ArrayList<>();



        myRef.child("SETS").child(category).child("questions").orderByChild("setNo").equalTo(setNo).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Log.d("Database","Retrieved");
                    list.add(snapshot1.getValue(QuestionModel.class));
                    //Log.d("List Datas",snapshot1.getValue(QuestionModel.class).getQuest());
                }
                if(list.size()>0){

                    playAnim(que,0,list.get(pos).getQuest());
                    for (int i=0;i<4;++i){
                        final int e3e=i;
                        layout.getChildAt(i).setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                String p23 = "Unsuccesful on Childs"+e3e;
                                // Log.d("Error1",p23);
                                checkAnswer((Button) v );
                            }
                        });
                    }
                    next.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View v) {
                            next.setAlpha(0.7f);
                            next.setEnabled(false);
                            enableOpt(true);
                            count= 0;pos++;
                            if(pos==list.size()) {
                                ///send to setsacivity

                                Log.d("end","EndofQueSet");
                                return ;
                            }

                            playAnim(que,0,list.get(pos).getQuest());
                        }
                    } );

                }
                else{
                    finish();
                    Toast.makeText(QuestionsActivity.this, "no questions", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(QuestionsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        scr.setText(""+score+"/"+list.size());


    }
    private void playAnim(final View view, final int value, final String data){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                String opt ="";
                if(value == 0 && count < 4){
                    if(count==0){
                        opt = list.get(pos).getOptA();
                    }
                    else if(count==1){
                        opt = list.get(pos).getOptB();
                    }
                    else if(count==2){
                        opt = list.get(pos).getOptC();
                    }
                    else if(count==3){
                        opt = list.get(pos).getOptD();
                    }

                    playAnim(layout.getChildAt(count),0,opt);
                    count++;
                }
            }

            @Override
            public void onAnimationEnd(Animator animation) {

                if(value == 0 ){
                    try{((TextView)view).setText(data);}
                    catch (ClassCastException e){
                        ((Button)view).setText(data);
                    }
                    view.setTag(data);
                    playAnim(view,1,data);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


    }
    private void checkAnswer(Button sopt){
        enableOpt(false);
        no_ques++;
        next.setEnabled(true);
        next.setAlpha(1);
        if(sopt.getText().toString().equals(list.get(pos).getAns())) {
            //correct
            score++;
            sopt.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00ee00")));
        }
        else {
            //sopt.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00ee00")));
            //incorrect
            sopt.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ee0000")));
            Button correctoption = (Button) layout.findViewWithTag(list.get(pos).getAns());

            correctoption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00ee00")));
        }

        scr.setText(""+score+"/"+list.size());
    }

    private void enableOpt(boolean enable){
        for (int i=0;i<4;++i){
           layout.getChildAt(i).setEnabled(enable);

            layout.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#989898")));
        }

    }
    /*
        list.add(new QuestionModel("How are you 1  ?","a","b","c","d","a"));
        list.add(new QuestionModel("How are you 2  ?","a","b","c","d","b"));
        list.add(new QuestionModel("How are you 3  ?","a","b","c","d","d"));
        list.add(new QuestionModel("How are you 4  ?","a","b","c","d","c"));
        list.add(new QuestionModel("How are you 5  ?","a","b","c","d","c"));
        list.add(new QuestionModel("How are you 6  ?","a","b","c","d","a"));
        list.add(new QuestionModel("How are you 7  ?","a","b","c","d","b"));
        list.add(new QuestionModel("How are you 8  ?","a","b","c","d","d"));
        list.add(new QuestionModel("How are you 9  ?","a","b","c","d","c"));
        list.add(new QuestionModel("How are you 10 ?","a","b","c","d","b"));



      */
}