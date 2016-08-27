package magio.ohmypet.temp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import magio.ohmypet.R;
import magio.ohmypet.model.ItemData;

/**
 * Created by mini on 2016-08-01.
 */
public class CustomAdapter extends BaseAdapter {
    private ArrayList<ItemData> itemDatas = null; //인자로 받아온 itemDatas를 저장하기 위한 어댑터 내의 ArrayList<ItemData> 객체
    private LayoutInflater layoutInflater = null; //ListView 아이템 레이아웃을 가져오기 위한 클래스
    private Context context = null;

    /*생성자를 통해서 itemDatas를 받아옵니다.
      context는 BaseAdapter에 내재되어 있지 않으므로 인자로 받아오니다.*/
    public CustomAdapter(ArrayList<ItemData> itemDatas, Context context){
        this.context = context;
        this.itemDatas = itemDatas;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //생성자를 통해서가 아닌 메소드를 통해 itemDatas를 받아오기 위한 메소드
    //이번 실습에서는 사용하지는 않으니 참고만 해주세요.
    private void setItemDatas(ArrayList<ItemData> itemDatas){
        this.itemDatas = itemDatas;
        this.notifyDataSetChanged();
    }

    //int getCount 메소드 -> ListView의 아이템 개수를 int형으로 반환
    @Override
    public int getCount() {
        return (itemDatas != null) ? itemDatas.size() : 0;
    }

    //Object getItem 메소드 -> position에 해당하는 아이템을 객체의 형태로 반환
    //이번 실습에서는 ListView의 아이템이 ItemData형이므로 getItem 메소드를 통해 Object형으로 데이터를 받아오면 temData형으로 캐스팅을 해주어야겠죠?
    @Override
    public Object getItem(int position) {
        return (itemDatas != null && (0 <= position && position < itemDatas.size()) ? itemDatas.get(position) : null ); // A ? B : C 아시조??
    }

    //long getItemId 메소드 -> position에 해당하는 아이템의 Id를 반환
    @Override
    public long getItemId(int position) {
        return (itemDatas != null && (0 <= position && position < itemDatas.size()) ? position : 0);
    }

    //View getView 메소드 -> 받아온 데이터(itemDatas)를 ListView의 아이템으로 만들어주는 메소드
    //convertView는 재활용이 가능한 View

    // Type 1
    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder(); //화면에서 사라진 convertView를 담아두기 위한 용도로 viewHolder를 사용

        if(convertView == null){ //'convertView == null' = 'convertView가 한번도 생성이 된 적 없다면'
            convertView = layoutInflater.inflate(R.layout.temp_list_item, parent, false); //TextView 2개를 포함하고 있는 list_item을 inflate해서 convertView에 대입

            //viewHolder를 초기화. 한번 해두면 이 position의 convertView는 다시 findViewById를 할 필요가 없어집니다.
            viewHolder.title = (TextView)convertView.findViewById(R.id.txtTitle_item);
            viewHolder.description = (TextView)convertView.findViewById(R.id.txtDescription_item);

            convertView.setTag(viewHolder); //convertView에 viewHolder를 태그로 달아줍니다.이렇게 해두면 화면에서 ListView의 아이템(convertView)가 사라져도 메모리에서 지워지지 않습니다.
        }
        else{
            viewHolder = (ViewHolder)convertView.getTag();//convertView가 존재한다면(한번이라도 생성이 되었다면) 달아뒀던 viewHolder가 존재하겠죠? 이를 다시 가져옵니다.
        }

        ItemData itemData = itemDatas.get(position); //position에 해당하는 itemData를 받아옵니다.

        viewHolder.TextView_Title.setText(itemData.Title); //받아온 itemData의 데이터를 viewHolder의 TextView에 띄웁니다.
        viewHolder.TextView_Description.setText(itemData.Description);

        return convertView; //헷갈리실 수 있는데 viewHolder를 반환하는게 아니라 convertView를 반환해야 합니다!! 사용하려고 inflate까지 했잖아요?!
    }*/ // Type 1

    // Type 2
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.temp_list_item, parent, false);
        }

        ImageView image = ViewHolderHelper.get(convertView, R.id.imageTitle_item);
        TextView text = ViewHolderHelper.get(convertView, R.id.txtTitle_item);
        TextView description = ViewHolderHelper.get(convertView, R.id.txtDescription_item);

        ItemData itemData = itemDatas.get(position); //position에 해당하는 itemData를 받아옵니다.

        int imageRes = 0;
        switch (itemData.getTestImage()) {
            case 0: imageRes = R.drawable.f; break;
            case 1: imageRes = R.drawable.g; break;
            case 2: imageRes = R.drawable.h; break;
            case 3: imageRes = R.drawable.i; break;
            case 4: imageRes = R.drawable.j; break;
            case 5: imageRes = R.drawable.k; break;
        }
        image.setImageResource(imageRes);
        text.setText(itemData.getTitle());
        description.setText(itemData.getDesc());

        return convertView;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Drawable image, String title, String desc) {
        ItemData item = new ItemData();

        item.setImage(image);
        item.setTitle(title);
        item.setDesc(desc);

        itemDatas.add(item);
    }
}
