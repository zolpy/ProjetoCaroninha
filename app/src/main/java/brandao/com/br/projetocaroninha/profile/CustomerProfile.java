package brandao.com.br.projetocaroninha.profile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import brandao.com.br.projetocaroninha.R;
import brandao.com.br.projetocaroninha.entidades.ECustomer;

//import com.google.firebase.storage;

public class CustomerProfile extends AppCompatActivity {

    FirebaseUser customer = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference databaseReference;
    private EditText edtCustomerName,edtPhoneCustomer,edtCustomerCPF;
    private ImageView ivCustomer;
    private Button btnConfirm;
    private ECustomer eCustomer;
    private Uri resultUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child("customers").child(String.valueOf(customer.getUid())).child("infoProprietario2");
        edtCustomerName = findViewById(R.id.edtCustomerName);
        edtPhoneCustomer = findViewById(R.id.edtPhoneCustomer);
        edtCustomerCPF = findViewById(R.id.edtCustomerCPF);
        ivCustomer = findViewById(R.id.ivCustomer);
        btnConfirm = findViewById(R.id.btnConfirm);

        eventoDatabase();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtCustomerName.getText().toString().trim().isEmpty() ||
                        edtPhoneCustomer.getText().toString().trim().isEmpty() )//||
                       // edtCustomerCPF.getText().toString().trim().isEmpty())
                        {
                    Toast.makeText(CustomerProfile.this, "Preencha os campos!", Toast.LENGTH_LONG).show();
                } else {
                    eCustomer = new ECustomer();
                    eCustomer.setName(edtCustomerName.getText().toString().trim());
                    eCustomer.setPhone(edtPhoneCustomer.getText().toString().trim());
                    //eCustomer.setCpf(edtCustomerCPF.getText().toString().trim());
                    databaseReference.setValue(eCustomer);

//                    if(resultUri != null){
//                        StorageReference filePath = FirebaseStorage.getInstance().getReference().child("profile_images").child(String.valueOf(customer.getUid()));
//                        Bitmap bitmap = null;
//
//                        try{
//                            bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(),resultUri);
//                        }catch (IOException e){
//                            e.printStackTrace();
//
//                        }
//
//                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                        bitmap.compress(Bitmap.CompressFormat.JPEG,20,baos);
//                        byte [] data = baos.toByteArray();
//                        UploadTask uploadTask  = filePath.putBytes(data);
//
//                        uploadTask.addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e){
//                                finish();
//                                return;
//
//                            }
//                            });
//
//                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                             Uri downloadUri = taskSnapshot.getDownloadUrl();
//
//                                Map newImage = new HashMap();
//                                newImage.put("profileImageUrl",downloadUri.toString());
//                                databaseReference.updateChildren(newImage);
//                                finish();
//                                return;
//                            }
//                        });
//
//
//
//                    } else {
//                        finish();
//                    }
                    finish(); //retirar daqui quando descomenar
                }
            }
        });

        ivCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);

            }
        });
    }

    private void eventoDatabase() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    ECustomer a = dataSnapshot.getValue(ECustomer.class);
                    edtCustomerName.setText(a.getName());
                    edtPhoneCustomer.setText(a.getPhone());
                   // edtCustomerCPF.setText(a.getCpf());

                } else {
                    Toast.makeText(CustomerProfile.this, "dataSnapshot.exists() não existe", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());

            }

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==1 && resultCode == Activity.RESULT_OK){
            final Uri imagemUri = data.getData();
            resultUri = imagemUri;
            ivCustomer.setImageURI(resultUri);
        }
    }
}
