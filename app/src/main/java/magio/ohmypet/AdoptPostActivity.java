package magio.ohmypet;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.io.IOException;

import magio.ohmypet.db.SqlHelper;
import magio.ohmypet.util.Constants;

public class AdoptPostActivity extends AppCompatActivity {

    static final int MAX_IMAGE_COUNT = 3;  // The request code
    static final int SELECT_PICTURE = 1;  // The request code

    private int imageCount = -1;
    private int curPage = -1;

    private Button btn_registPost;
    private Button btn_registerImage;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopt_post);

        initialize();

        initDB();
        initHandler();


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

    private void initialize() {


        editName = (EditText) findViewById(R.id.adopt_post_title);
        editPrice = (EditText) findViewById(R.id.adopt_post_price);
        editDesc = (EditText) findViewById(R.id.adopt_post_desc);
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

        btn_registPost = (Button)findViewById(R.id.adopt_post_register);
        if (btn_registPost != null) {
            btn_registPost.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    // TODO 등록하기
                    // insert into 테이블명 values (값, 값, 값...);
                    String name = editName.getText().toString();
                    String price = editPrice.getText().toString();
                    String desc = editDesc.getText().toString();

                    String sql = "insert into " +  SqlHelper.DB_NAME + " values(null, '" + name + "', " + price + ", '" + desc + "');";
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
