package magio.ohmypet;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import magio.ohmypet.model.ItemData;

/**
 * Created by mini on 2016-08-03.
 */

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private ArrayList<ItemData> albumList;
    private int itemLayout;

    /**
     * 생성자
     * @param items
     * @param itemLayout
     */
    public MyRecyclerAdapter(ArrayList<ItemData> items , int itemLayout){

        this.albumList = items;
        this.itemLayout = itemLayout;
    }

    /**
     * 레이아웃을 만들어서 Holer에 저장
     * @param viewGroup
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(itemLayout,viewGroup,false);
        return new ViewHolder(view);
    }

    /**
     * listView getView 를 대체
     * 넘겨 받은 데이터를 화면에 출력하는 역할
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        ItemData item = albumList.get(position);

        int imageRes = 0;
        switch (item.getTestImage()) {
            case 0: imageRes = R.drawable.f; break;
            case 1: imageRes = R.drawable.g; break;
            case 2: imageRes = R.drawable.h; break;
            case 3: imageRes = R.drawable.i; break;
            case 4: imageRes = R.drawable.j; break;
            case 5: imageRes = R.drawable.k; break;
        }

        viewHolder.image.setBackgroundResource(imageRes);
        viewHolder.title.setText(item.getTitle());
        viewHolder.description.setText(item.getDesc());

        viewHolder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }

    /**
     * 뷰 재활용을 위한 viewHolder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title;
        TextView description;

        public ViewHolder(View itemView){
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.imageTitle_item2);
            title = (TextView) itemView.findViewById(R.id.txtTitle_item2);
            description = (TextView) itemView.findViewById(R.id.txtDescription_item2);
        }

    }
}


