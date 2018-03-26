package com.mercadolibre.android.ui.example.ui.widgets.textfield;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.mercadolibre.android.ui.example.BaseActivity;
import com.mercadolibre.android.ui.example.R;
import com.mercadolibre.android.ui.widgets.TextField;

public class TextFieldActivity extends BaseActivity {

    TextField textField;
    TextField superTextField;
    TextField centerTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textfield);

        textField = (TextField) findViewById(R.id.text_field);
        superTextField = (TextField) findViewById(R.id.super_text_field);
        centerTextField = (TextField)findViewById(R.id.text_field_center);

        centerTextField.addTextChangedListener(new TextFieldWatcher(centerTextField));
        superTextField.addTextChangedListener(new TextFieldWatcher(superTextField));
        textField.addTextChangedListener(new TextFieldWatcher(textField));
        textField.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                showKeyboard();
            }
        });
        textField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showKeyboard();
            }
        });
    }

    void showKeyboard(){
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
                .showSoftInput(textField, InputMethodManager.SHOW_FORCED);
    }

    static class TextFieldWatcher implements TextWatcher {
        private TextField textField;

        TextFieldWatcher(TextField textField) {
            this.textField = textField;
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if ("error".equals(s.toString()) || s.length() == 0) {
                textField.setError("An error ocurred");
            }
        }
    }
}
