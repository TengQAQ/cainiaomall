package com.teng.cainiaomall.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.teng.cainiaomall.Activity.MainActivity;
import com.teng.cainiaomall.Dao.Good_Dao;
import com.teng.cainiaomall.Model.Good;
import com.teng.cainiaomall.R;

import org.angmarch.views.NiceSpinner;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;
import static com.teng.cainiaomall.Fragment.MineFragment.TAKE_CAMERA;

public class SellFragment extends Fragment {
    private Uri imageUri;//原图保存地址
    ImageView imageView;
    String imagdate;
    private NiceSpinner niceSpinner;
    EditText g_name,g_describe,g_money,g_sx;
    TextView commit;
    String g_name1,g_describe1,g_money1,g_sx1,nicespinner21="类型一",savepath;
    long text1;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd_HHmmss");//获取当前时间戳
    Date date = new Date(System.currentTimeMillis());
    List<String> spinnerData = new LinkedList<>(Arrays.asList("类型一", "类型二", "类型三", "类型四",
            "类型五", "类型六", "类型七", "类型八", "类型九", "类型十"));


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_sell, container, false);
        niceSpinner = view.findViewById(R.id.nice_spinner);
        imageView = view.findViewById(R.id.g_picture);
        g_name = view.findViewById(R.id.g_name);
        g_describe = view.findViewById(R.id.g_describe);
        g_money = view.findViewById(R.id.g_money);
        g_sx = view.findViewById(R.id.g_sl);
        commit = view.findViewById(R.id.button_commit);

        niceSpinner.attachDataSource(spinnerData);
        niceSpinner.setBackgroundResource(R.drawable.textview_round_border);
        niceSpinner.setTextColor(Color.BLACK);
        niceSpinner.setTextSize(22);
        niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nicespinner21 = spinnerData.get(position);
                Log.i(TAG, "ces2: "+nicespinner21);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE}, 0);
        }
//        invitview();
        view.findViewById(R.id.button_takephoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File imagePath = new File(getActivity().getFilesDir(), "myimages");//gn file_paths.xml文件中的files-path标签中的path值一致
                if (!imagePath.exists()) {
                    imagePath.mkdirs();
                }
                File newFile = new File(imagePath, simpleDateFormat.format(date) + ".jpg");//getFilesDir()/myimages/imagdate.jpg
                imagdate=simpleDateFormat.format(date);
                savepath="myimages"+"/"+simpleDateFormat.format(date) + ".jpg";
                System.out.println("newFile.getPath()="+newFile.getPath());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //大于等于版本24（7.0）的场合
                    imageUri = FileProvider.getUriForFile(getActivity(), "com.teng.cainiaomall.fileprovider", newFile);
                    //此处的outputImage指定的路径要在file_paths.xml文件对应配置path指定路径的子路径
                } else {
                    //小于android 版本7.0（24）的场合
                    imageUri = Uri.fromFile(newFile);
                }
                //启动相机程序
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //MediaStore.ACTION_IMAGE_CAPTURE = android.media.action.IMAGE_CAPTURE
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(intent, TAKE_CAMERA);
            }
        });

        commit.setOnClickListener(v -> {
            g_name1= g_name.getText().toString();
            g_money1= g_money.getText().toString();
            g_describe1= g_describe.getText().toString();
            g_sx1= g_sx.getText().toString();

            SharedPreferences sharedPreferences=getActivity().getSharedPreferences("user_rememberpass", Context.MODE_PRIVATE);
            String user_id=sharedPreferences.getString("username","");
            Log.i(TAG, "onCreateView: "+user_id);

            Good good =new Good();
            good.setGood_name(g_name1);
            good.setGood_describe(g_describe1);
            good.setGood_price(Double.valueOf(g_money1));
            good.setGood_sx(g_sx1);
            good.setGood_ztype(nicespinner21);
            good.setGood_time(imagdate);
            good.setGood_picpath(savepath);
            good.setGood_user_id(user_id);
            Good_Dao good_dao =new Good_Dao(getActivity());
            text1 = good_dao.addgood(good);
            Intent intent =new Intent();
            intent.setClass(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//它可以关掉所要到的界面中间的activity
            startActivity(intent);
        });







        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_CAMERA:
                if (resultCode == RESULT_OK) {
                    try {
                        // 将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri));
                        imageView.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:break;
        }
    }

}