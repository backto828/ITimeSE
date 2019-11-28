package com.chen.practice.itimese.others;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.chen.practice.itimese.R;

public class RepeatCycleDialog extends Dialog {
    public interface DialogEventListener {
        void DialogEvent(int day);
    }

    private Context context;
    private DialogEventListener listener;
    private EditText editText;

    public RepeatCycleDialog(Context context, DialogEventListener listener) {
        super(context, R.style.dialog);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_repeat_cycle, null);

        ((TextView) layout.findViewById(R.id.repeat_day_customize_dialog_title)).setText("周期");

        editText = layout.findViewById(R.id.repeat_day_customize_edit_text);

        layout.findViewById(R.id.repeat_day_customize_cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        layout.findViewById(R.id.repeat_day_customize_confirm_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int day;
                String str = editText.getText().toString();
                if (str.isEmpty()) {
                    day = 0;
                } else {
                    day = Integer.parseInt(str);
                }

                listener.DialogEvent(day);
                dismiss();
            }
        });

        this.setContentView(layout);
    }
}