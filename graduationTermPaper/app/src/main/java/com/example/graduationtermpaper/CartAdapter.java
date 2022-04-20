package com.example.graduationtermpaper;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private final Context context;
    private final LayoutInflater myInflater;
    private final Button buttonCheckOut;
    private GlobalCount globalCount;
    private PrefData prefData;


    public CartAdapter(Context context, Button buttonCheckOut) {
        myInflater = LayoutInflater.from(context);
        this.context = context;
        this.buttonCheckOut = buttonCheckOut;
        globalCount = new GlobalCount(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = myInflater.inflate(R.layout.cartitem_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(myView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        prefData = new PrefData(context,position);
        if (prefData.getShowFlag()!=false){
            holder.numberPicker.setMinValue(0);
            holder.numberPicker.setMaxValue(10);
            holder.numberPicker.setValue(prefData.getNumber());
            holder.numberPicker.setWrapSelectorWheel(false);
            holder.numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    if (newVal!=0){
                        prefData = new PrefData(context,position);
                        prefData.setNumber(newVal);
                        prefData.setPrice((prefData.getUnitPrice()+prefData.getAddPrice()+prefData.getSidePrice()+ prefData.getDrinkPrice())*prefData.getNumber());
                        holder.cartPrice.setText("NT$"+prefData.getPrice());
                        setButtonCheckOut();
                    }else { //==0
                        prefData = new PrefData(context,position);
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("刪除");
                        builder.setMessage("你確定要刪除嗎?");
                        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                prefData = new PrefData(context,position);
                                prefData.clear();
                                prefData.setShowFlag(false);
                                setButtonCheckOut();
                                holder.itemView.setVisibility(View.GONE);
                                holder.itemView.getLayoutParams().height = 0; //可以即時移除此項item
                                dialog.dismiss();
                            }
                        });
                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                prefData = new PrefData(context,position);
                                holder.numberPicker.setValue(1);
                                prefData.setNumber(1);
                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    }

                }
            });
//            //減少NumberPicker在ScrollView無法滑動的亂象吧?
//            holder.numberPicker.setOnTouchListener(new View.OnTouchListener() {
//
//                @Override
//                public boolean onTouch(final View v, final MotionEvent event) {
//                    if (event.getAction() == MotionEvent.ACTION_MOVE && v.getParent() != null) {
//                        v.getParent().requestDisallowInterceptTouchEvent(true);
//                    }
//                    if (event.getAction() == MotionEvent.ACTION_UP) {
////                        v.performClick();//不用顯示數字鍵盤
//                    }
//
//                    v.onTouchEvent(event);
//                    return true;
//                }
//            });

            holder.cartTitle.setText(prefData.getFood());
            holder.cartPrice.setText("NT$"+prefData.getPrice());

            if (prefData.getIndex()>=0&&prefData.getIndex()<=16){
                toastToBread(holder.toastTitle, holder.toastDetail, holder.breadTitle, holder.breadDetail);
                cheeseToSauce(holder.cheeseTitle, holder.cheeseDetail, holder.addTitle, holder.addDetail, holder.veggieTitle,
                        holder.veggieDetail, holder.pickleTitle, holder.pickleDetail, holder.sauceTitle, holder.sauceDetail);
                sideToDrink(holder.sideTitle, holder.sideDetail, holder.drinkTitle, holder.drinkDetail);
            }

            if (prefData.getIndex()>=17&&prefData.getIndex()<=20){
                holder.toastTitle.setVisibility(View.GONE);
                holder.toastDetail.setVisibility(View.GONE);
                holder.breadTitle.setVisibility(View.GONE);
                holder.breadDetail.setVisibility(View.GONE);

                cheeseToSauce(holder.cheeseTitle, holder.cheeseDetail, holder.addTitle, holder.addDetail, holder.veggieTitle,
                        holder.veggieDetail, holder.pickleTitle, holder.pickleDetail, holder.sauceTitle, holder.sauceDetail);
                sideToDrink(holder.sideTitle, holder.sideDetail, holder.drinkTitle, holder.drinkDetail);
            }

            if (prefData.getIndex()>=21&&prefData.getIndex()<=22){
                holder.toastTitle.setVisibility(View.GONE);
                holder.toastDetail.setVisibility(View.GONE);
                holder.breadTitle.setVisibility(View.GONE);
                holder.breadDetail.setVisibility(View.GONE);
                holder.cheeseTitle.setVisibility(View.GONE);
                holder.cheeseDetail.setVisibility(View.GONE);
                holder.addTitle.setVisibility(View.GONE);
                holder.addDetail.setVisibility(View.GONE);
                holder.veggieTitle.setVisibility(View.GONE);
                holder.veggieDetail.setVisibility(View.GONE);
                holder.pickleTitle.setVisibility(View.GONE);
                holder.pickleDetail.setVisibility(View.GONE);
                holder.sauceTitle.setVisibility(View.GONE);
                holder.sauceDetail.setVisibility(View.GONE);

                sideToDrink(holder.sideTitle, holder.sideDetail, holder.drinkTitle, holder.drinkDetail);
            }

            if (prefData.getIndex()>=23&&prefData.getIndex()<=30){
                holder.containerDetail.setVisibility(View.GONE);
            }

                setButtonCheckOut();



        }else{
           holder.itemView.setVisibility(View.GONE);
           holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));//只能放此，用在即時移除會閃退
            //holder.itemView.getLayoutParams().height = 0; //可以即時移除此項item，所以也可用此招
//           這不用到，只是個紀錄點:
//                       recyclerView.post(new Runnable()
//            {
//                @Override
//                public void run() {
//                    notifyDataSetChanged();
//                }
//            });

        }

        holder.containerDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,foodSelection.class);
                PrefData prefData1 = new PrefData(context,position);//PrefData要選擇該位置，否則會取最後一筆
                intent.putExtra("food",prefData1.getFood());
                intent.putExtra("price",prefData1.getPrice());
                intent.putExtra("description",prefData1.getDescription());
                intent.putExtra("picture",prefData1.getPicture());
                intent.putExtra("index",prefData1.getIndex());
                intent.putExtra("itemNo",position);
                context.startActivity(intent);
            }
        });

    }

    public void toastToBread(TextView toastTitle,TextView toastDetail,TextView breadTitle,TextView breadDetail){
        toastTitle.setText(prefData.getToastTitle());
        toastDetail.setText(prefData.getToast());
        breadTitle.setText(prefData.getBreadTitle());
        breadDetail.setText(prefData.getBread());
    }

    public void cheeseToSauce(TextView cheeseTitle,TextView cheeseDetail,TextView addTitle,TextView addDetail,
                              TextView veggieTitle,TextView veggieDetail,TextView pickleTitle,TextView pickleDetail,
                              TextView sauceTitle,TextView sauceDetail){
        cheeseTitle.setText(prefData.getCheeseTitle());
        cheeseDetail.setText(prefData.getCheese());

        String[] add = prefData.getAdd();
        if (add.length!=0){
            addTitle.setText(prefData.getAddTitle());
            addTitle.append("(NT$"+prefData.getAddPrice()+")");
            addDetail.setText("");
            for (int i=0;i<add.length;i++){
                if (i!=add.length-1){
                    addDetail.append(add[i]+"\n");
                }else {
                    addDetail.append(add[i]);
                }

            }
        } else {
            addTitle.setVisibility(View.GONE);
            addDetail.setVisibility(View.GONE);
        }

        veggieTitle.setText(prefData.getVeggieTitle());
        String[] veggie = prefData.getVeggie();
        veggieDetail.setText("");
        if (veggie.length!=0){
            for (int i=0;i<veggie.length;i++){
                if (i!=veggie.length-1){
                    veggieDetail.append(veggie[i]+"\n");
                }else{
                    veggieDetail.append(veggie[i]);
                }
            }
        }else{
            veggieDetail.append("全不加蔬菜Without Veggie");
        }

        pickleTitle.setText(prefData.getPickleTitle());
        String[] pickle = prefData.getPickle();
        pickleDetail.setText("");
        if (pickle.length!=0){
            for (int i=0;i<pickle.length;i++){
                if(i!=pickle.length-1){
                    pickleDetail.append(pickle[i]+"\n");
                }else {
                    pickleDetail.append(pickle[i]);
                }

            }
        }else {
            pickleDetail.append("全不加醃製物Without Pickles");
        }

        sauceTitle.setText(prefData.getSauceTitle());
        String[] sauce = prefData.getSauce();
        sauceDetail.setText("");
        if (sauce.length!=0){
            for (int i=0;i<sauce.length;i++){
                if (i!=sauce.length-1){
                    sauceDetail.append(sauce[i]+"\n");
                }else{
                    sauceDetail.append(sauce[i]);
                }

            }
        } else {
            sauceDetail.append("全不加醬汁Without Sauce");
        }
    }

    public void sideToDrink(TextView sideTitle, TextView sideDetail, TextView drinkTitle, TextView drinkDetail){
        sideTitle.setText(prefData.getSideTitle());
        sideTitle.append("(NT$"+prefData.getSidePrice()+")");
        sideDetail.setText(prefData.getSide());
        drinkTitle.setText(prefData.getDrinkTitle());
        drinkTitle.append("(NT$"+prefData.getDrinkPrice()+")");
        drinkDetail.setText(prefData.getDrink());
    }

    public void setButtonCheckOut(){
        int total = 0;
        for (int i=0;i<globalCount.getCount();i++){
            PrefData prefData = new PrefData(context,i);
            if (prefData.getShowFlag()!=false){
                total += prefData.getPrice();
            }
        }
        buttonCheckOut.setText("總共NT$"+total+"，結帳去");
    }

    @Override
    public int getItemCount() {
        return globalCount.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private final NumberPicker numberPicker;
        private final TextView cartTitle,cartPrice,toastTitle,toastDetail,breadTitle,breadDetail,cheeseTitle,cheeseDetail,
        addTitle,addDetail,veggieTitle,veggieDetail,pickleTitle,pickleDetail,sauceTitle,sauceDetail,
        sideTitle,sideDetail,drinkTitle,drinkDetail;
        private final LinearLayout containerDetail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            numberPicker = (NumberPicker) itemView.findViewById(R.id.numberPicker_cart);
            cartTitle = (TextView) itemView.findViewById(R.id.textView_cartTitle);
            cartPrice = (TextView) itemView.findViewById(R.id.textView_cartPrice);
            toastTitle = (TextView) itemView.findViewById(R.id.textView_toastTitle);
            toastDetail = (TextView) itemView.findViewById(R.id.textView_toastDetail);
            breadTitle = (TextView) itemView.findViewById(R.id.textView_breadTitle);
            breadDetail = (TextView) itemView.findViewById(R.id.textView_breadDetail);
            cheeseTitle = (TextView) itemView.findViewById(R.id.textView_cheeseTitle);
            cheeseDetail = (TextView) itemView.findViewById(R.id.textView_cheeseDetail);
            addTitle = (TextView) itemView.findViewById(R.id.textView_addTitle);
            addDetail = (TextView) itemView.findViewById(R.id.textView_addDetail);
            veggieTitle = (TextView) itemView.findViewById(R.id.textView_veggieTitle);
            veggieDetail = (TextView) itemView.findViewById(R.id.textView_veggieDetail);
            pickleTitle = (TextView) itemView.findViewById(R.id.textView_pickleTitle);
            pickleDetail = (TextView) itemView.findViewById(R.id.textView_pickleDetail);
            sauceTitle = (TextView) itemView.findViewById(R.id.textView_sauceTitle);
            sauceDetail = (TextView) itemView.findViewById(R.id.textView_sauceDetail);
            sideTitle = (TextView) itemView.findViewById(R.id.textView_sideTitle);
            sideDetail = (TextView) itemView.findViewById(R.id.textView_sideDetail);
            drinkTitle = (TextView) itemView.findViewById(R.id.textView_drinkTitle);
            drinkDetail = (TextView) itemView.findViewById(R.id.textView_drinkDetail);
            containerDetail = (LinearLayout) itemView.findViewById(R.id.container_detail);


        }
    }
}
