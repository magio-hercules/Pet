package magio.ohmypet.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.io.IOException;

import magio.ohmypet.Fragment.CustomMapFragment;
import magio.ohmypet.R;
import magio.ohmypet.db.SqlHelper;
import magio.ohmypet.util.CommonUtil;
import magio.ohmypet.util.Constants;

public class AdoptPostActivity extends AppCompatActivity {

    static final int MAX_IMAGE_COUNT = 3;  // The request code
    static final int SELECT_PICTURE = 1;  // The request code

    private int imageCount = -1;
    private int curPage = -1;

    private Button btn_registPost;
    private Button btn_registerImage;
    private FloatingActionButton btn_registerPost;

    // DB에 저장 될 속성을 입력받는다
    private EditText editName;
    private EditText editPrice;
    private EditText editDesc;

    // 쿼리 결과 입력
    private TextView tvResult;

    private ViewFlipper viewFlipper;

    private float lastX;

    // for DB
    private SqlHelper sqlHelper;

    ScrollView scrollViewPost;

    // 값 셋팅시, StackOverFlow를 막기 위해서, 바뀐 변수를 저장해준다.
    String priceStr="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt_post);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        initialize();

        initActionBar();

        initDB();
        initHandler();

        initMap();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            ImageView image= null;
            // flipper에 등록하기
            switch(requestCode){
                case SELECT_PICTURE:
                    Uri imageUri = data.getData();
                    if (imageCount < MAX_IMAGE_COUNT) {
                        imageCount++;
                    } else {
                        return;
                    }

                    switch (imageCount) {
                        case 0:
                            image = (ImageView)findViewById(R.id.imageView1);
                            curPage = 0;
                            break;
                        case 1:
                            image = (ImageView)findViewById(R.id.imageView2);
                            break;
                        case 2:
                            image = (ImageView)findViewById(R.id.imageView3);
                            btn_registerImage.setEnabled(false);
                            break;
                    }
//                    image.setImageURI(imageUri);
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);

                        Bitmap newBitmap = resizeBitmapImageFn(bitmap, 800);
                        image.setImageBitmap(newBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void initialize() {
        editName = (EditText) findViewById(R.id.adopt_post_title);
        editPrice = (EditText) findViewById(R.id.adopt_post_price);
        editDesc = (EditText) findViewById(R.id.adopt_post_desc);
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(true);

        actionBar.setTitle(R.string.adopt_post);
    }
    private void initDB() {
        try {
            sqlHelper = new SqlHelper(getApplicationContext(), "pet.db", null, 1);
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e(Constants.TAG, "Can't open DB");
            finish();
        }

        // for test
        // 쿼리 결과 입력
        tvResult = (TextView) findViewById(R.id.tv_result);
        tvResult.setText( sqlHelper.PrintData() );
    }


    private void initHandler() {

        // FAB
        btn_registerPost = (FloatingActionButton) findViewById(R.id.fab_adopt_post);
        if (btn_registerPost != null) {
            btn_registerPost.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    // TODO 등록하기
                    // insert into 테이블명 values (값, 값, 값...);
                    String name = editName.getText().toString();
                    String price = editPrice.getText().toString();
                    String desc = editDesc.getText().toString();

                    if (name.equals("")) {
                        CommonUtil.Toast(AdoptPostActivity.this, "Title을 입력해주세요");
                        return;
                    }

                    if (price.equals("")) {
                        price = "0";
                    }

                    String sql = "insert into " +  SqlHelper.DB_NAME + " values(null, '" + name + "', " + Integer.parseInt(price) + ", '" + desc + "');";
                    sqlHelper.insert(sql);

                    Log.i(Constants.TAG, "Insert DB sql : " + sql);
                    tvResult.setText( sqlHelper.PrintData() );
                }
            });
        }

        btn_registerImage = (Button)findViewById(R.id.adopt_post_register_image);
        if (btn_registerImage != null) {
           btn_registerImage.setOnClickListener(new Button.OnClickListener() {

               public void onClick(View v) {
                   Intent intent = new Intent();
                   intent.setType("image/*");
                   intent.setAction(Intent.ACTION_GET_CONTENT);
                   intent.putExtra(Intent.EXTRA_TEXT, "Select Picture");
//                   startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
                   startActivityForResult(intent, SELECT_PICTURE);
               }
           });
        }

        viewFlipper = (ViewFlipper) findViewById(R.id.adopt_post_image_view);
//        viewFlipper.setFlipInterval(10000);
//        viewFlipper.startFlipping();
        viewFlipper.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
//                viewFlipper.showNext();
            }
        });
        viewFlipper.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                onTouchEvent(event);
                return false;
            }
        });

        editPrice.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if(!s.toString().equals(priceStr)){     // StackOverflow를 막기위해,
//                    if (s.length() == 0) {
//                        priceStr = "";
//                        return;
//                    }
//                    priceStr = CommonUtil.DecimalFormat().format(Long.parseLong(s.toString().replaceAll(",", "")));   // 에딧텍스트의 값을 변환하여, result에 저장.
//                    editPrice.setText(priceStr);    // 결과 텍스트 셋팅.
//                    editPrice.setSelection(priceStr.length());     // 커서를 제일 끝으로 보냄.
//                }
            }
        });
    }


    public void initMap() {
        scrollViewPost = (ScrollView)findViewById(R.id.adopt_post_scroll);

        // Google map init block
        CustomMapFragment customMapFragmentPost = ((CustomMapFragment) getSupportFragmentManager().findFragmentById(R.id.adopt_post_map));
        customMapFragmentPost.setOnTouchListener(new CustomMapFragment.OnTouchListener(){
            @Override
            public void onTouch() {
                scrollViewPost.requestDisallowInterceptTouchEvent(true);
            }
        });
    }

    /*
     * 비트맵(Bitmap) 이미지의 가로, 세로 이미지를 리사이징
     * @param bmpSource 원본 Bitmap 객체
     * @param maxResolution 제한 해상도
     * @return 리사이즈된 이미지 Bitmap 객체
     */
    public Bitmap resizeBitmapImageFn(Bitmap bmpSource, int maxResolution){
        int iWidth = bmpSource.getWidth();      //비트맵이미지의 넓이
        int iHeight = bmpSource.getHeight();     //비트맵이미지의 높이
        int newWidth = iWidth ;
        int newHeight = iHeight ;
        float rate = 0.0f;

        //이미지의 가로 세로 비율에 맞게 조절
        if(iWidth > iHeight ){
            if(maxResolution < iWidth ){
                rate = maxResolution / (float) iWidth ;
                newHeight = (int) (iHeight * rate);
                newWidth = maxResolution;
            }
        }else{
            if(maxResolution < iHeight ){
                rate = maxResolution / (float) iHeight ;
                newWidth = (int) (iWidth * rate);
                newHeight = maxResolution;
            }
        }

        return Bitmap.createScaledBitmap(
                bmpSource, newWidth, newHeight, true);
    }


    // Method to handle touch event like left to right swap and right to left swap
    public boolean onTouchEvent(MotionEvent touchevent)
    {
        switch (touchevent.getAction())
        {
            // when user first touches the screen to swap
            case MotionEvent.ACTION_DOWN:
            {
                lastX = touchevent.getX();
                Log.d(Constants.TAG, "lastX : " + lastX);
                return true;
//                break;
            }
            case MotionEvent.ACTION_UP:
            {
                float currentX = touchevent.getX();
                Log.d(Constants.TAG, "currentX : " + currentX);


                // if left to right swipe on screen
                if (lastX < currentX && currentX - lastX > 30)
                {
                    // If no more View/Child to flip
                    if (viewFlipper.getDisplayedChild() == 0 || imageCount == 0)
                        break;

                    // set the required Animation type to ViewFlipper
                    // The Next screen will come in form Left and current Screen will go OUT from Right
                    viewFlipper.setInAnimation(this, R.anim.in_from_left);
                    viewFlipper.setOutAnimation(this, R.anim.out_to_right);
                    viewFlipper.showPrevious();
                    curPage--;
                }

                // if right to left swipe on screen
                if (lastX > currentX && lastX - currentX > 30)
                {
                    if (viewFlipper.getDisplayedChild() == MAX_IMAGE_COUNT - 1 || imageCount == curPage)
                        break;
                    // set the required Animation type to ViewFlipper
                    // The Next screen will come in form Right and current Screen will go OUT from Left
                    viewFlipper.setInAnimation(this, R.anim.in_from_right);
                    viewFlipper.setOutAnimation(this, R.anim.out_to_left);
                    viewFlipper.showNext();
                    curPage++;
                }
                break;
            }
        }
        return false;
    }
}
