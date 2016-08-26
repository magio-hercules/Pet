package magio.ohmypet;

//import android.app.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import magio.ohmypet.model.ItemData;
import magio.ohmypet.util.Constants;

/**
 * Created by mini on 2016-07-06.
 */
public class TabFragment2 extends Fragment{
    private RecyclerView recyclerView;
    private ArrayList<ItemData> itemDatas = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab2, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);

        initModel();
        initData();

        final GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });


        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                Log.d(Constants.TAG, "onInterceptTouchEvent");
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                /*Log.d(TAG, "getChildAdapterPosition=>" + rv.getChildAdapterPosition(child));
                Log.d(TAG,"getChildLayoutPosition=>"+rv.getChildLayoutPosition(child));
                Log.d(TAG,"getChildViewHolder=>" + rv.getChildViewHolder(child));*/
                    //Toast.makeText(getApplication(), count.get(rv.getChildAdapterPosition(child)).toString(), Toast.LENGTH_SHORT).show();
                    Log.d(Constants.TAG, "AdapterPosition=>" + rv.findViewHolderForAdapterPosition(rv.getChildLayoutPosition(child)));
                    Log.d(Constants.TAG, "LayoutPosition=>" + rv.findViewHolderForLayoutPosition(rv.getChildLayoutPosition(child)));
                    Log.d(Constants.TAG, "getChildViewHolder=>" + rv.getChildViewHolder(child).itemView);
                    //TextView tv = (TextView) rv.findViewHolderForAdapterPosition(rv.getChildLayoutPosition(child)).itemView.findViewById(R.id.tv);
                    //TextView tv = (TextView) rv.findViewHolderForLayoutPosition(rv.getChildLayoutPosition(child)).itemView.findViewById(R.id.tv);
                    TextView txt = (TextView) rv.getChildViewHolder(child).itemView.findViewById(R.id.txtTitle_item2);
                    TextView desc = (TextView) rv.getChildViewHolder(child).itemView.findViewById(R.id.txtDescription_item2);

                    Toast.makeText(getActivity(), txt.getText() + "\n" + desc.getText(), Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                Log.d(Constants.TAG, "onTouchEvent");
            }
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                Log.d(Constants.TAG, "onRequestDisallowInterceptTouchEvent");
            }
        });

        return v;
    }

    private void initModel() {
        this.itemDatas = new ArrayList<ItemData>();

        int i;
        for(i = 0; i < 1000; i++) {
            ItemData itemData_submit = new ItemData();
            itemData_submit.setTestImage((int)(Math.random() * 6));
            itemData_submit.setTitle("Test " + i);
            itemData_submit.setDesc("Desc " + i);

            itemDatas.add(0, itemData_submit);
        }
    }

    private void initData(){
        recyclerView.setAdapter(new MyRecyclerAdapter(itemDatas, R.layout.card_item));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }



}
