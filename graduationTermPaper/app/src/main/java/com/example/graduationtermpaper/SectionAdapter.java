package com.example.graduationtermpaper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.ViewHolder> {

    private final LayoutInflater myInflater;
    private final Context context;
    private final List<String> titleList;
    private final List<List<String>> optionList, toCheckList;
    private final Button buttonAddToCart;
    private final ActivityResultLauncher<Intent> getResult;
    private PrefData prefData;

    boolean toastFlag=false,breadFlag=false,cheeseFlag=false,addFlag=false,veggieFlag=false,
            pickleFlag=false,sauceFlag=false,sideFlag=false,drinkFlag=false;
    List<Boolean> flagList;

    String[] secondTitle;
    List<String> secondTitleList;
    //手動修改
    TextView textView1,textView2,textView3;
    List<TextView> textViewList;
    Button button1,button2,button3;
    List<Button> buttonList;
    private int choice;
    //private String[] dataArray1, dataArray2,dataArray3;
    //private List<String[]> dataArrayList;

    public SectionAdapter(Context context, List<String> titleList, List<List<String>> optionList, List<List<String>> toCheckList,Button buttonAddToCart,ActivityResultLauncher<Intent> getResult,PrefData prefData) {
        myInflater = LayoutInflater.from(context);
        this.context = context;
        this.titleList = titleList;
        this.optionList = optionList;
        this.toCheckList = toCheckList;
        this.buttonAddToCart = buttonAddToCart;
        this.getResult = getResult;
        this.prefData = prefData;

        flagList = new ArrayList<>();
        flagList.add(toastFlag);
        flagList.add(breadFlag);
        flagList.add(cheeseFlag);
        flagList.add(addFlag);
        flagList.add(veggieFlag);
        flagList.add(pickleFlag);
        flagList.add(sauceFlag);
        flagList.add(sideFlag);
        flagList.add(drinkFlag);

        secondTitle = context.getResources().getStringArray(R.array.secondTitle);
        secondTitleList = new ArrayList<>();
        secondTitleList = Arrays.asList(secondTitle);

        textView1 = new TextView(context);
        textView2 = new TextView(context);
        textView3 = new TextView(context);
        textViewList = new ArrayList<>();
        textViewList.add(textView1);
        textViewList.add(textView2);
        textViewList.add(textView3);

        button1 = new Button(context);
        button2 = new Button(context);
        button3 = new Button(context);
        buttonList = new ArrayList<>();
        buttonList.add(button1);
        buttonList.add(button2);
        buttonList.add(button3);

        //dataArray1 = new String[]{};
        //dataArray2 = new String[]{};
        //dataArray3 = new String[]{};
        //dataArrayList = new ArrayList<String[]>();
        //dataArrayList.add(dataArray1);
        //dataArrayList.add(dataArray2);
        //dataArrayList.add(dataArray3);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = myInflater.inflate(R.layout.sectionitem_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(myView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        String title = titleList.get(position);
        holder.showTitle.setText(title);

        if (prefData.getIndex()>=0&&prefData.getIndex()<=16){ //是主食
            if (position != 3){ //不是checkbox
                setRadioGroup(position, holder.radioGroup, holder.radioButton, holder.layoutParams, holder.container);
            }else{ //checkbox
                holder.showSubTitle.setText("最多可選5種");
                setCheckbox(position,holder.checkboxList, holder.layoutParams, holder.container);
            }
        }

        if (prefData.getIndex() >= 17 && prefData.getIndex() <= 20) { //是沙拉
            if (position != 1) { //不是checkbox
                setRadioGroup(position, holder.radioGroup, holder.radioButton, holder.layoutParams, holder.container);
                } else { //checkbox
                holder.showSubTitle.setText("最多可選5種");
                    setCheckbox(position,holder.checkboxList, holder.layoutParams, holder.container);
                }
            }

        if (prefData.getIndex() >= 21 && prefData.getIndex() <= 22) { //是輕食,全是radioButton
            setRadioGroup(position, holder.radioGroup, holder.radioButton, holder.layoutParams, holder.container);
        }

    }

    public void setRadioGroup(int position,RadioGroup radioGroup,RadioButton radioButton,RadioGroup.LayoutParams layoutParams,LinearLayout container){
        radioGroup = new RadioGroup(context);
        radioGroup.setOrientation(LinearLayout.VERTICAL);
        radioGroup.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        choice = -1;
        for (int i = 0; i < optionList.get(position).size(); i++) {

            radioButton = new RadioButton(context);
            radioButton.setText(optionList.get(position).get(i));

            radioButton.setPadding(30, 0, 0, 0);

            layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(0, 40, 0, 0);

            radioGroup.addView(radioButton, layoutParams);


            if (prefData.getShowFlag()){

                if (prefData.getShowFlag()){
                    for (int j=0;j<flagList.size();j++)
                        flagList.set(j,true);
                }
                choice = setEditRadioButton(prefData,radioButton,radioGroup);
            }
        }

        container.addView(radioGroup);

        if (prefData.getShowFlag()){
            if (choice==0){
        container.addView(textViewList.get(0));
        container.addView(buttonList.get(0));
            }
            if (choice==1){
                container.addView(textViewList.get(1));
                container.addView(buttonList.get(1));
            }
            if (choice==2){
                container.addView(textViewList.get(2));
                container.addView(buttonList.get(2));
            }
            for (int i=0;i<secondTitleList.size();i++){
                int j = i;
                buttonList.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context,SecondActivity.class);
                        if (j==0){
                            intent.putExtra("title", secondTitleList.get(0));
                            intent.putExtra("message", prefData.getVeggie());
                        }
                        if (j==1){
                            intent.putExtra("title", secondTitleList.get(1));
                            intent.putExtra("message", prefData.getPickle());
                        }
                        if (j==2){
                            intent.putExtra("title", secondTitleList.get(2));
                            intent.putExtra("message", prefData.getSauce());
                        }
                        //intent.putExtra("message", dataArrayList.get(j));
                        intent.putExtra("index", prefData.getIndex());
                        getResult.launch(intent);
                    }
                });
            }
        }

        radioGroup.setOnCheckedChangeListener(new MyRadioListener(container,radioGroup));

    }

    private class MyRadioListener implements RadioGroup.OnCheckedChangeListener {
        LinearLayout container;
        RadioGroup radioGroup;

        public MyRadioListener(LinearLayout container, RadioGroup radioGroup) {
            this.container = container;
            this.radioGroup = radioGroup;
        }

        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton checkedRadioButton = radioGroup.findViewById(checkedId);
            int checkedRadioButtonId = radioGroup.indexOfChild(checkedRadioButton);
            String checkedRadioButtonText = checkedRadioButton.getText().toString();

//            Toast.makeText(context, "text:" + checkedRadioButtonText + "\nid:" + checkedRadioButtonId, Toast.LENGTH_SHORT).show();

            if (checkedRadioButtonText.equals(toCheckList.get(0).get(0))||
                    checkedRadioButtonText.equals(toCheckList.get(0).get(1))){ //toast
                flagList.set(0,true);
                setButtonAddToCart();
                prefData.setToast(checkedRadioButtonText);
            }

            if (checkedRadioButtonText.equals(toCheckList.get(1).get(0))||
                    checkedRadioButtonText.equals(toCheckList.get(1).get(1))||
                    checkedRadioButtonText.equals(toCheckList.get(1).get(2))||
                    checkedRadioButtonText.equals(toCheckList.get(1).get(3))){ //bread
                flagList.set(1,true);
                setButtonAddToCart();
                prefData.setBread(checkedRadioButtonText);
            }

            if (checkedRadioButtonText.equals(toCheckList.get(2).get(0))||
                    checkedRadioButtonText.equals(toCheckList.get(2).get(1))){ //cheese
                flagList.set(2,true);
                setButtonAddToCart();
                prefData.setCheese(checkedRadioButtonText);
            }

            if (checkedRadioButtonText.equals(toCheckList.get(4).get(0))||
                    checkedRadioButtonText.equals(toCheckList.get(4).get(1))){ //veggie
                flagList.set(4,true);
                setButtonAddToCart();
                if (checkedRadioButtonText.equals(toCheckList.get(4).get(0))){
                    prefData.setVeggie(new String[]{});
                }
            }

            if (checkedRadioButtonText.equals(toCheckList.get(5).get(0))||
                    checkedRadioButtonText.equals(toCheckList.get(5).get(0))){ //pickle
               flagList.set(5,true);
                setButtonAddToCart();
                if (checkedRadioButtonText.equals(toCheckList.get(5).get(0))){
                    prefData.setPickle(new String[]{});
                }
            }

            if (checkedRadioButtonText.equals(toCheckList.get(6).get(0))||
                    checkedRadioButtonText.equals(toCheckList.get(6).get(1))){ //sauce
                flagList.set(6,true);
                setButtonAddToCart();
                if (checkedRadioButtonText.equals(toCheckList.get(6).get(0))){
                    prefData.setSauce(new String[]{});
                }
            }

            if (checkedRadioButtonText.equals(toCheckList.get(7).get(0))||
                    checkedRadioButtonText.equals(toCheckList.get(7).get(1))||
                    checkedRadioButtonText.equals(toCheckList.get(7).get(2))||
                    checkedRadioButtonText.equals(toCheckList.get(7).get(3))||
                    checkedRadioButtonText.equals(toCheckList.get(7).get(4))||
                    checkedRadioButtonText.equals(toCheckList.get(7).get(5))){ //side
                flagList.set(7,true);
                setButtonAddToCart();
                prefData.setSide(checkedRadioButtonText);

                if (checkedRadioButtonText.equals(toCheckList.get(7).get(0))){ //+0
                    prefData.setPrice(prefData.getPrice()-(prefData.getSidePrice()*prefData.getNumber()));
                    prefData.setSidePrice(0);
                }
                if (checkedRadioButtonText.equals(toCheckList.get(7).get(1))){ //+15
                    prefData.setPrice(prefData.getPrice()-(prefData.getSidePrice()*prefData.getNumber())+(15*prefData.getNumber()));
                    prefData.setSidePrice(15);
                }
                if (checkedRadioButtonText.equals(toCheckList.get(7).get(2))||
                        checkedRadioButtonText.equals(toCheckList.get(7).get(3))||
                        checkedRadioButtonText.equals(toCheckList.get(7).get(4))||
                        checkedRadioButtonText.equals(toCheckList.get(7).get(5))){ //+25
                    prefData.setPrice(prefData.getPrice()-(prefData.getSidePrice()*prefData.getNumber())+(25*prefData.getNumber()));
                    prefData.setSidePrice(25);
                }
//                Toast.makeText(context, "sidePrice:"+prefData.getSidePrice(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, "price:"+prefData.getPrice(), Toast.LENGTH_SHORT).show();
            }

            if (checkedRadioButtonText.equals(toCheckList.get(8).get(0))||
                    checkedRadioButtonText.equals(toCheckList.get(8).get(1))||
                    checkedRadioButtonText.equals(toCheckList.get(8).get(2))||
                    checkedRadioButtonText.equals(toCheckList.get(8).get(3))||
                    checkedRadioButtonText.equals(toCheckList.get(8).get(4))){ //drink
                flagList.set(8,true);
                setButtonAddToCart();
                prefData.setDrink(checkedRadioButtonText);

                if (checkedRadioButtonText.equals(toCheckList.get(8).get(0))||
                        checkedRadioButtonText.equals(toCheckList.get(8).get(1))||
                        checkedRadioButtonText.equals(toCheckList.get(8).get(2))||
                        checkedRadioButtonText.equals(toCheckList.get(8).get(3))){ //+0
                    prefData.setPrice(prefData.getPrice()-(prefData.getDrinkPrice()*prefData.getNumber()));
                    prefData.setDrinkPrice(0);
                }
                if (checkedRadioButtonText.equals(toCheckList.get(8).get(4))){ //+5
                    prefData.setPrice(prefData.getPrice()-(prefData.getDrinkPrice()*prefData.getNumber())+(5*prefData.getNumber()));
                    prefData.setDrinkPrice(5);
                }
//                Toast.makeText(context, "drinkPrice:"+prefData.getDrinkPrice(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, "price:"+prefData.getPrice(), Toast.LENGTH_SHORT).show();
            }

            for (int i = 0; i < secondTitleList.size(); i++) {
                if (checkedRadioButtonText.equals(secondTitleList.get(i))) {
                    Intent intent = new Intent(context, SecondActivity.class);
                    intent.putExtra("title", checkedRadioButtonText);
                    intent.putExtra("index", prefData.getIndex());
                    getResult.launch(intent);

                    textViewList.get(i).setVisibility(View.GONE);
                    buttonList.get(i).setVisibility(View.GONE);
                    container.addView(textViewList.get(i));
                    container.addView(buttonList.get(i));
                    int j = i;
                    buttonList.get(i).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (checkedRadioButtonText.equals(secondTitleList.get(0))){
                                intent.putExtra("message", prefData.getVeggie());
                            }
                            if (checkedRadioButtonText.equals(secondTitleList.get(1))){
                                intent.putExtra("message", prefData.getPickle());
                            }
                            if (checkedRadioButtonText.equals(secondTitleList.get(2))){
                                intent.putExtra("message", prefData.getSauce());
                            }
                            //intent.putExtra("message", dataArrayList.get(j));
                            intent.putExtra("index", prefData.getIndex());
                            getResult.launch(intent);
                        }
                    });
                }
            }

            if (!checkedRadioButtonText.equals(secondTitleList.get(0)) &&
                    !checkedRadioButtonText.equals(secondTitleList.get(1)) &&
                    !checkedRadioButtonText.equals(secondTitleList.get(2))) { //這要手動修改
                for (int i = 0; i < secondTitleList.size(); i++) {
                    container.removeView(buttonList.get(i));
                    container.removeView(textViewList.get(i));
                }
            }

        }

    }

    public void setCheckbox(int position,List<CheckBox> checkboxList,RadioGroup.LayoutParams layoutParams,LinearLayout container){
        checkboxList = new ArrayList<CheckBox>();
        for (int i = 0; i < optionList.get(position).size(); i++) {
            checkboxList.add(new CheckBox(context));
            checkboxList.get(i).setText(optionList.get(position).get(i));
            if (prefData.getShowFlag()){
                setEditCheckBox(prefData,checkboxList.get(i));
            }
            checkboxList.get(i).setPadding(30, 0, 0, 0);
            layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(0, 40, 0, 0);
            container.addView(checkboxList.get(i), layoutParams);
        }
        for (int i = 0; i < checkboxList.size(); i++) {
            checkboxList.get(i).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { //Add
                    String text = buttonView.getText().toString();
                    if (isChecked == true) {
//                        Toast.makeText(context, "checked=" + text, Toast.LENGTH_SHORT).show();
                        prefData.setAdd(text);
                        if (text.equals(toCheckList.get(3).get(0))){//+20
                            prefData.setPrice(prefData.getPrice()+(20*prefData.getNumber()));
                            prefData.setAddPrice(prefData.getAddPrice()+20);
                        }
                        if (text.equals(toCheckList.get(3).get(1))||
                            text.equals(toCheckList.get(3).get(2))) {//+25
                            prefData.setPrice(prefData.getPrice()+(25*prefData.getNumber()));
                            prefData.setAddPrice(prefData.getAddPrice()+25);
                        }
                        if (text.equals(toCheckList.get(3).get(3))){ //+30
                            prefData.setPrice(prefData.getPrice()+(30*prefData.getNumber()));
                            prefData.setAddPrice(prefData.getAddPrice()+30);
                        }
                        if (text.equals(toCheckList.get(3).get(4))){ //+35
                            prefData.setPrice(prefData.getPrice()+(35*prefData.getNumber()));
                            prefData.setAddPrice(prefData.getAddPrice()+35);
                        }
//                        Toast.makeText(context, "addPrice:"+prefData.getAddPrice(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(context, "price:"+prefData.getPrice(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(context, "size:"+prefData.getAdd().length, Toast.LENGTH_SHORT).show();
                    } else {
//                        Toast.makeText(context, "unchecked=" + text, Toast.LENGTH_SHORT).show();
                        prefData.removeAdd(text);
                        for (int i=0;i<prefData.getAdd().length;i++){
//                            Toast.makeText(context, "after:"+prefData.getAdd()[i], Toast.LENGTH_SHORT).show();
                        }
                        if (text.equals(toCheckList.get(3).get(0))){//-20
                            prefData.setPrice(prefData.getPrice()-(20*prefData.getNumber()));
                            prefData.setAddPrice(prefData.getAddPrice()-20);
                        }
                        if (text.equals(toCheckList.get(3).get(1))||
                                text.equals(toCheckList.get(3).get(2))) {//-25
                            prefData.setPrice(prefData.getPrice()-(25*prefData.getNumber()));
                            prefData.setAddPrice(prefData.getAddPrice()-25);
                        }
                        if (text.equals(toCheckList.get(3).get(3))){ //-30
                            prefData.setPrice(prefData.getPrice()-(30*prefData.getNumber()));
                            prefData.setAddPrice(prefData.getAddPrice()-30);
                        }
                        if (text.equals(toCheckList.get(3).get(4))){ //-35
                            prefData.setPrice(prefData.getPrice()-(35*prefData.getNumber()));
                            prefData.setAddPrice(prefData.getAddPrice()-35);
                        }
//                        Toast.makeText(context, "addPrice:"+prefData.getAddPrice(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(context, "price:"+prefData.getPrice(), Toast.LENGTH_SHORT).show();
//                        Toast.makeText(context, "size:"+prefData.getAdd().length, Toast.LENGTH_SHORT).show();
                    }
                    if (text.equals(toCheckList.get(3).get(0))||
                            text.equals(toCheckList.get(3).get(1))||
                            text.equals(toCheckList.get(3).get(2))||
                            text.equals(toCheckList.get(3).get(3))||
                            text.equals(toCheckList.get(3).get(4))){ //add//其實可省略這段
                        flagList.set(3,true);

                    }
                }
            });
        }
    }

    public void setButton(String title,String[] message) {

        for (int i=0;i<secondTitleList.size();i++) {
            if (title.equals(secondTitleList.get(i))) { //"item1-3"
                //dataArrayList.set(i, message);
                if (message.length == 0) {
                    buttonList.get(i).setText("Required to choose");//button1
                    //手動
                    if(title.equals(secondTitleList.get(0))){
                        flagList.set(4,false);
                        setButtonAddToCart();
                        prefData.setVeggie(new String[]{});
                    }
                    if(title.equals(secondTitleList.get(1))){
                        flagList.set(5,false);
                        setButtonAddToCart();
                        prefData.setPickle(new String[]{});
                    }
                    if(title.equals(secondTitleList.get(2))){
                        flagList.set(6,false);
                        setButtonAddToCart();
                        prefData.setSauce(new String[]{});
                    }
                    //////////////
                } else {
                    buttonList.get(i).setText("Edit " + secondTitleList.get(i));//button1:"item1-3"
                    //手動
                    if(title.equals(secondTitleList.get(0))){
                        flagList.set(4,true);
                        setButtonAddToCart();
                        prefData.setVeggie(message);//dataArrayList.get(0)
                    }
                    if(title.equals(secondTitleList.get(1))){
                        flagList.set(5,true);
                        setButtonAddToCart();
                        prefData.setPickle(message);//dataArrayList.get(1)
                    }
                    if(title.equals(secondTitleList.get(2))){
                        flagList.set(6,true);
                        setButtonAddToCart();
                        prefData.setSauce(message);//dataArrayList.get(2)
                    }
                    //////////////
                }
                buttonList.get(i).setVisibility(View.VISIBLE);//button1
                textViewList.get(i).setText("You choose:");// textView1
                for (int j = 0; j < message.length; j++) {

                    if (j != message.length - 1) {
                        textViewList.get(i).append(message[j] + ","); //textView1
                    } else {
                        textViewList.get(i).append(message[j]); //textView1
                    }

                }
                textViewList.get(i).setVisibility(View.VISIBLE); //textView1
            }
        }

    }

    public void  setButtonAddToCart(){

        if (prefData.getIndex()>=0&&prefData.getIndex()<=16){
            if (flagList.get(0)&&flagList.get(1)&&flagList.get(2)&&
                    flagList.get(4)&&flagList.get(5)&&flagList.get(6)&&
                    flagList.get(7)&&flagList.get(8)){
                buttonAddToCart.setEnabled(true);
            }else{
                buttonAddToCart.setEnabled(false);
            }
        }
        if (prefData.getIndex()>=17&&prefData.getIndex()<=20){
            if (flagList.get(2)&&flagList.get(4)&&flagList.get(5)&&
                flagList.get(6)&&flagList.get(7)&&flagList.get(8)){
                buttonAddToCart.setEnabled(true);
            }else {
                buttonAddToCart.setEnabled(false);
            }
        }
        if (prefData.getIndex()>=21&&prefData.getIndex()<=22){
            if (flagList.get(7)&&flagList.get(8)){
                buttonAddToCart.setEnabled(true);
            }else {
                buttonAddToCart.setEnabled(false);
            }
        }

    }

    public int setEditRadioButton(PrefData prefData,RadioButton radioButton,RadioGroup radioGroup){
        if (radioButton.getText().toString().equals(prefData.getToast())||
            radioButton.getText().toString().equals(prefData.getBread())||
            radioButton.getText().toString().equals(prefData.getCheese())||
            radioButton.getText().toString().equals(prefData.getSide())||
                radioButton.getText().toString().equals(prefData.getDrink())){
            radioGroup.check(radioButton.getId());
            return -1;
        }
        if (prefData.getVeggie().length!=0){
            if (radioButton.getText().toString().equals(toCheckList.get(4).get(1))){//"加蔬菜With Veggie(請選擇您要的蔬菜)"
                radioGroup.check(radioButton.getId());
//                buttonList.set(0,new Button(context));
//                textViewList.set(0,new TextView(context));
                setButton(secondTitleList.get(0),prefData.getVeggie());
                return 0;
            }
        } else {
            if (radioButton.getText().toString().equals(toCheckList.get(4).get(0))){//"全不加蔬菜Without Veggie"
                radioGroup.check(radioButton.getId());
                return -1;
            }
        }
        if (prefData.getPickle().length!=0){
            if (radioButton.getText().toString().equals(toCheckList.get(5).get(1))){//"加醃製物With Pickles(請選擇您要的醃製物)"
                radioGroup.check(radioButton.getId());
                setButton(secondTitleList.get(1),prefData.getPickle());
                return 1;
            }
        }else {
            if (radioButton.getText().toString().equals(toCheckList.get(5).get(0))){//"全不加醃製物Without Pickles"
                radioGroup.check(radioButton.getId());
                return -1;
            }
        }
        if (prefData.getSauce().length!=0){
                if (radioButton.getText().toString().equals(toCheckList.get(6).get(1))) {//"加醬汁With Sauce(請選擇您要的醬汁)"
                    radioGroup.check(radioButton.getId());
                    setButton(secondTitleList.get(2), prefData.getSauce());
                    return 2;
                }
        }else {
            if (radioButton.getText().toString().equals(toCheckList.get(6).get(0))){//"全不加醬汁Without Sauce"
                radioGroup.check(radioButton.getId());
                return -1;
            }
        }
        return -1;
    }

    public void setEditCheckBox(PrefData prefData,CheckBox checkBox){
        if (prefData.getAdd().length!=0){
            for (int i=0;i<prefData.getAdd().length;i++){
                if (checkBox.getText().toString().equals(prefData.getAdd()[i])){
                    checkBox.setChecked(true);
                }
            }

        }
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView showTitle;
        private final ImageButton showButton;
        private LinearLayout container;
        TextView showSubTitle;
        RadioGroup radioGroup;
        RadioButton radioButton;
        RadioGroup.LayoutParams layoutParams;
        List<CheckBox> checkboxList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            showTitle = itemView.findViewById(R.id.textView_showTitle);
            showButton = itemView.findViewById(R.id.imageButton_show);
            container = itemView.findViewById(R.id.myContainer);
            showSubTitle = itemView.findViewById(R.id.textView_showSubTitle);

            showButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(container.getVisibility()==View.VISIBLE){
                        container.setVisibility(View.GONE);
                        showButton.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    }else{
                        container.setVisibility(View.VISIBLE);
                        showButton.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    }
                }
            });

        }
    }



}
