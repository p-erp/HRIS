package com.example.hr_payroll.admin.admin_company_fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hr_payroll.R;
import com.example.hr_payroll.admin.admin_company_fragment.admin_company_adapter.Admin_Company_Adapter;
import com.example.hr_payroll.model.Company;
import com.example.hr_payroll.utilities.Functions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class Admin_Company_Fragment extends Fragment implements Admin_Company_FragmentMVP.Fragment {

        private Admin_Company_Adapter admin_company_adapter;
        private Admin_Company_Fragment_Presenter presenter;
        private Functions functions;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        admin_company_adapter  = new Admin_Company_Adapter(getActivity());
        presenter = new Admin_Company_Fragment_Presenter(this,getActivity());
        functions = new Functions(getActivity());
        return LayoutInflater.from(getActivity()).inflate(R.layout.admin_company_fragment,container,false);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onGetCompanies();
    }

    @BindView(R.id.recycleview)
    RecyclerView recyclerView;
    @BindView(R.id.add_company)
    FloatingActionButton btn_add_company;
    @OnClick(R.id.add_company)
    void addCompany(){
        showCustomDialog(getActivity());
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(admin_company_adapter);
    }


    @Override
    public void GetCompaniesSuccess(ArrayList<Company> companies) {
        admin_company_adapter.SetData(companies);

    }

    @Override
    public void GetCompaniesFailed(String title, String message) {

    }

    @Override
    public void SaveCompanySuccess(String title, String message) {
        functions.showMessage(title,message);
    }

    @Override
    public void SaveCompanyFailed(String title, String message) {
        functions.showMessage(title,message);

    }

    private Dialog dialog;
    private View customDialogView;
    private CircleImageView image;

    private EditText name,address,mobile,landline,email,password,re_password;
    private Bitmap bitmap;
    private static int REQUEST_CODE = 1;
    public void showCustomDialog(final Context context) {
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        customDialogView = inflater.inflate(R.layout.dialog_admin_add_company, null, false);
        image = customDialogView.findViewById(R.id.image);

        name = customDialogView.findViewById(R.id.tf_name);
        address = customDialogView.findViewById(R.id.tf_address);
        mobile = customDialogView.findViewById(R.id.tf_mobile);
        landline = customDialogView.findViewById(R.id.tf_landline);
        email = customDialogView.findViewById(R.id.tf_email);
        password = customDialogView.findViewById(R.id.tf_password);
        re_password = customDialogView.findViewById(R.id.tf_repassword);


        image.setPadding(50,50,50,50);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                getActivity().startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE);
            }
        });

        Button btn_add_company= customDialogView.findViewById(R.id.add_company);

        btn_add_company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(functions.isEmpty(name)){
                    functions.showMessage("Failed","Empty Name");
                }else if(functions.isEmpty(address)){
                    functions.showMessage("Failed","Empty Address");
                }else if(functions.isEmpty(mobile)){
                    functions.showMessage("Failed","Empty Mobile");
                }else if(functions.isEmpty(landline)){
                    functions.showMessage("Failed","Empty LandLine");
                }else if(functions.isEmpty(email)){
                    functions.showMessage("Failed","Empty Email");
                }else if(functions.isEmpty(password)){
                    functions.showMessage("Failed","Empty Password");
                }else if(functions.isEmpty(re_password)){
                    functions.showMessage("Failed","Must Type password Twice");
                }else if(!functions.isValid(email.getText().toString())){
                    functions.showMessage("Failed","Invalid Email");
                }else if(!functions.isMatch(password.getText().toString(),re_password.getText().toString())){
                    functions.showMessage("Failed","Password Doesnt Match");
                }else if(imageUri== null){
                    functions.showMessage("Failed","Empty Image");
                }else{
                    Company company = new Company();
                    String _name = name.getText().toString();
                    String _address = address.getText().toString();
                    String _mobile = mobile.getText().toString();
                    String _landline = landline.getText().toString();
                    String _email = email.getText().toString();
                    String _password = password.getText().toString();
                    String _image = bitMapString();
                    company.setName(_name);
                    company.setAddress(_address);
                    company.setMobile(_mobile);
                    company.setLandline(_landline);
                    company.setEmail(_email);
                    company.setPassword(_password);
                    company.setImage(String.valueOf(imageUri));
                    presenter.onSaveCompany(company);

                }

            }
        });

        ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dialog.setContentView(customDialogView);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setGravity(Gravity.CENTER);
        dialog.show();
    }
    private String bitMapString(){

        ByteArrayOutputStream byteArrayOutputStreamObject ;
        byteArrayOutputStreamObject = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStreamObject);
        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();
        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);
        return ConvertImage;
    }
    private Uri imageUri;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE && data != null) {
            Uri path = data.getData();
            CropImage.activity(path).setAspectRatio(1,1).start(getActivity());
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                image.setPadding(0,0,0,0);
                imageUri = result.getUri();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                Glide.with(getActivity()).load(imageUri).into(image);
            }
        }

    }
}
