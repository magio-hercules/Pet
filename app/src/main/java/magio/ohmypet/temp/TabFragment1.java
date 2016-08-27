package magio.ohmypet.temp;

//import android.app.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import magio.ohmypet.R;
import magio.ohmypet.model.ItemData;

/**
 * Created by mini on 2016-07-06.
 */
public class TabFragment1 extends Fragment{
    /*ListView에 들어갈 ArrayList<ItemData>형의 객체 선언
     *ArrayList<ItemData> -> Title과 Description 두 가지의 String 객체를 데이터로 갖는 ItemData라는 객체들을 배열의 형태로 저장하는 클래스
     */
    private ArrayList<ItemData> itemDatas = null;

    //사용자와 직접 상호작용 하는 View들 선언
//    Button btnSubmit; //ListView에 데이터를 띄우기 위한 버튼
//    EditText editTitle, editDescription; //사용자로부터 Title과 Description을 받아오기 위한 EditText
    ListView listView; //데이터들을 list의 형태로 보여 주기 위한 view

    //ListView와 Model(itemDatas)을 연결해주는 CustomAdapter 선언
    private CustomAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.temp_tab1, container, false);


        initModel(); //ListView에 들어갈 데이터들(위에서 ArrayList<ItemData>의 형태로 선언한 itemDatas)를 초기화하는 모듈
        initDummy();
        initView(v); //Button, EditText, ListView 3가지 view들을 초기화시키고 Listener를 달아주는 모듈
        makeList(); //Model(itemDatas)와 View(ListView)를 이어주는 역할을 하는 모듈

        return v;
    }

    private void initDummy(){

        int i;
        for(i = 0; i < 1000; i++) {
            ItemData itemData_submit = new ItemData();
            itemData_submit.setTestImage((int)(Math.random() * 6));
            itemData_submit.setTitle("Test " + i);
            itemData_submit.setDesc("Desc " + i);

            itemDatas.add(0, itemData_submit);
        }

    }

    private void initModel() {
        itemDatas = new ArrayList<ItemData>(); //itemDatas 데이터 생성.
    }

    private void makeList() {

//        adapter = new CustomAdapter(itemDatas, getApplicationContext()); //CustomAdapter 객체인 adapter에 itemDatas와 Context를 인자로 넘겨주었습니다!!
        adapter = new CustomAdapter(itemDatas, getActivity()); //CustomAdapter 객체인 adapter에 itemDatas와 Context를 인자로 넘겨주었습니다!!
        listView.setAdapter(adapter); //listView에 우리가 만든 CustomAdapter 객체인 adapter를 적용시켰습니다. 이제 Model(itemDatas와 listView가 연결되었습니다!!)

    }

    private void initView(View v) {
//
//        //findViewById로 초기화해줍니다. 이제 이 코드들은 눈 감고도 칠 수 있어야 합니다.
//        editTitle = (EditText)v.findViewById(R.id.editTitle);
//        editDescription = (EditText)v.findViewById(R.id.editDescription);
//        btnSubmit = (Button)v.findViewById(R.id.btnSubmit);
        listView = (ListView)v.findViewById(R.id.listView);
//
//        btnSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                /*만약 사용자가 EditText에 아무것도 입력하지 않은 채 실수로 submit 버튼을 누르면 어떡하죠??
//                  이를 위해 예외 처리를 해주도록 하겠습니다.
//                 */
//                if(TextUtils.isEmpty(editTitle.getText()) || TextUtils.isEmpty(editDescription.getText())){
////                    Toast.makeText(getApplicationContext(),"데이터를 입력하셔야 합니다.",Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getActivity(),"데이터를 입력하셔야 합니다.",Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                else {
//                    /*EditText를 통해 사용자로부터 String 형태의 Title과 Description을 받아오면 이것들을 저장할 변수 또는 객체가 필요합니다.
//                      String 형의 Title과 Description 둘을 동시에 저장하기 위해 ItemData라는 클래스를 만들었으니 활용해야죠.
//                     */
//
//                    ItemData itemData_submit = new ItemData(); //ItemData 객체 itemData_submit를 생성. submit될 것이므로 이름이 헷갈리지 않게 _submit를 덧붙였습니다.
//                    itemData_submit.Title = editTitle.getText().toString(); //itemData_submit의 Title 멤버변수에 EditText의 내용 저장
//                    itemData_submit.Description = editDescription.getText().toString(); //itemData_submit의 Description 멤버변수에 EditText의 내용 저장
//
//                    /*itemData_submit에 사용자가 입력했던 Title과 Description이 저장되었습니다.
//                      이제 이 녀석을 ArrayList<ItemData> 객체인 itemDatas에 하나의 배열 데이터로 추가합니다.
//                     */
//
//                    itemDatas.add(0, itemData_submit);
//
//                    adapter.notifyDataSetChanged(); //itemDatas에 데이터가 추가되었으니 이를 adapter에 알립립닏립니다.
//
//                    editTitle.setText("");
//                    editDescription.setText("");
//
//                    // TODO: 2015-10-12 ListView 아이템을 클릭한 경우의 이벤트
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            ItemData itemData_temp = (ItemData)adapter.getItem(position); //getItem메소드는 Object형으로 반환을 하므로 ItemData형으로 캐스팅합니다.
//                            Toast.makeText(getApplicationContext(), itemData_temp.Title + "\n" + itemData_temp.Description, Toast.LENGTH_SHORT).show();
                            Toast.makeText(getActivity(), itemData_temp.getTitle() + "\n" + itemData_temp.getDesc(), Toast.LENGTH_SHORT).show();
                        }

                    });
//
//                    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//                        @Override
//                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
//                            ItemData itemData_temp = (ItemData)adapter.getItem(position);
//                            itemDatas.remove(itemData_temp); //itemDatas에서 해당 데이터를 가진 부분을 삭제합니다.
//                            adapter.notifyDataSetChanged(); //itemDatas에 데이터가 삭제되었으니 이를 adapter에 알립니다
//
//                            return false;
//                        }
//                    });
//                }
//            }
//
//        });
    }
}
