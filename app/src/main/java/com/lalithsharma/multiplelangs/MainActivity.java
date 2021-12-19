package com.lalithsharma.multiplelangs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.languageid.FirebaseLanguageIdentification;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView mSourceLang;
    private EditText mSourcetext;
    private Button mTranslateBtn;
    private TextView mTranslatedText;
    private String sourceText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSourceLang= findViewById(R.id.sourceLang);
        mSourcetext = findViewById(R.id.sourceText);
        mTranslateBtn = findViewById(R.id.translate);
        mTranslatedText = findViewById(R.id.translatedText);

        mTranslateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                identifyLanguge();
            }
        });

    }

    private void identifyLanguge() {
        sourceText = mSourcetext.getText().toString();
        FirebaseLanguageIdentification identifier = FirebaseNaturalLanguage .getInstance().
                getLanguageIdentification();

        identifier.identifyLanguage(sourceText).addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(@NonNull String s) {
                if(s.equals("und")){
                    Toast.makeText(getApplicationContext(),"Language Not identified",Toast.LENGTH_SHORT).show();
                }
                 else{
                     getLanguageCode(s);
                }
            }
        });



    }

    private void getLanguageCode(String language) {
        int  langCode ;
        switch (language){
            case"hi":
                langCode  = FirebaseTranslateLanguage.HI;
                break;
            case"ar":
                langCode = FirebaseTranslateLanguage.AR;
                break;
            case"ur":
                langCode = FirebaseTranslateLanguage.UR;
                break;
            default:
                langCode = 0;
            }
            translateText(langCode);


        }

    private void translateText(int langCode) {
        FirebaseTranslatorOptions options = new FirebaseTranslatorOptions().Builder().setSourceLanguage(langCode).setTargetLanguage(FirebaseTranslateLanguage.EN).build();

        FirebaseTranslator translator = FirebaseNaturalLanguage.getInstance().getTranslator(options);

        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions().Builder().build();
        translator.downloadModelIfNeeded(conditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(@NonNull Void unused) {

            }
        });

    }
}
