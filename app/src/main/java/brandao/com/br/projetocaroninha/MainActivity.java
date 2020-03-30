package brandao.com.br.projetocaroninha;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import brandao.com.br.projetocaroninha.Maps.CustomerMap;
import brandao.com.br.projetocaroninha.Maps.DriverMap;
import brandao.com.br.projetocaroninha.config.ConfigFirebase;
import brandao.com.br.projetocaroninha.entidades.ECustomer;
import brandao.com.br.projetocaroninha.entidades.EDriver;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    Button btnSignInDivrer, btnRegisterDivrer,btnSignInCustomer,btnRegisterCustomer;
    LinearLayout linearLayoutDriver, linearLayoutCustomer;
    TableLayout TableLayoutDriver,TableLayoutCustomers;
    RelativeLayout rootLayout;
    ImageView image_person, image_driver;

    private ECustomer caroneiro;
    private EDriver motorista;
    private FirebaseAuth auth,autenticacao;

    FirebaseDatabase db;
    DatabaseReference motoristas,caroneiros;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;


    //add a fonte aqui nesse @Override depois de Pressionar CTRL+O e escrever "attachBaseContext"
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().setDefaultFontPath("fonts/arkhip.ttf").setFontAttrId(R.attr.fontPath).build());
        setContentView(R.layout.activity_main);

        //iniciar Firebase
        auth = FirebaseAuth.getInstance();

        db = FirebaseDatabase.getInstance();
        motoristas = db.getReference();
        caroneiros = db.getReference();

        //Chamando
        btnSignInDivrer = findViewById(R.id.btnSignInDivrer);
        btnRegisterDivrer = findViewById(R.id.btnRegisterDivrer);
        btnSignInCustomer = findViewById(R.id.btnSignInCustomer);
        btnRegisterCustomer = findViewById(R.id.btnRegisterCustomer);
        linearLayoutDriver = findViewById(R.id.LinearLayoutDriver);
        linearLayoutCustomer = findViewById(R.id.LinearLayoutCustomer);
        TableLayoutCustomers = findViewById(R.id.TableLayoutCustomers);
        TableLayoutDriver = findViewById(R.id.TableLayoutDriver);
        rootLayout = findViewById(R.id.rootLayout);
        image_person = findViewById(R.id.image_person_off);
        image_driver = findViewById(R.id.image_driver_off);

//        //Verifica se o usuario está logado
//        if(motorista.isDriver()){
//            verifyDriverLogged();
//        } else if(caroneiro.isCustomer()){
//            verifyCustomerLogged();
//        }

        modeCustomer();

        //Event image
        image_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                modeDriver();
            }
        });

        image_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                modeCustomer();
            }
        });

    }

    /****event modeCustomer *****/
    private void modeCustomer(){
        linearLayoutCustomer.setVisibility(View.VISIBLE);
        linearLayoutDriver.setVisibility(View.INVISIBLE);
        TableLayoutCustomers.setVisibility(View.VISIBLE);
        TableLayoutDriver.setVisibility(View.INVISIBLE);

        //verifyCustomerLogged();

        //Event Button
        btnSignInCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerSignIn();

            }
        });

        btnRegisterCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerRegister();

            }
        });
    }

    /****=========event modeDriver=================================================*****/
    private void modeDriver(){
        linearLayoutDriver.setVisibility(View.VISIBLE);
        linearLayoutCustomer.setVisibility(View.INVISIBLE);
        TableLayoutDriver.setVisibility(View.VISIBLE);
        TableLayoutCustomers.setVisibility(View.INVISIBLE);

       // verifyDriverLogged();

        //Event Button
        btnRegisterDivrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driverRegister();
            }
        });

        btnSignInDivrer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                driverSignIn();
            }
        });
    }

    /****=========Login Driver==================================================*****/
    private void driverSignIn() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("SIGN IN DRIVER");
        dialog.setMessage("Please use email to sign in");

        LayoutInflater inflater = LayoutInflater.from(this);
        View login_layout = inflater.inflate(R.layout.layout_login, null);

        final MaterialEditText edtEmail = login_layout.findViewById(R.id.edtEmail);
        final MaterialEditText edtSenha = login_layout.findViewById(R.id.edtSenha);

        dialog.setView(login_layout);

        //Dialog setPositiveButton
        dialog.setPositiveButton("SIGN IN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();


                //Check Validation
                if (TextUtils.isEmpty(edtEmail.getText().toString().trim())) {
                    Snackbar.make(rootLayout, "Please enter email address", Snackbar.LENGTH_SHORT).show();

                    //return;
                }
                if (TextUtils.isEmpty(edtSenha.getText().toString().trim())) {
                    Snackbar.make(rootLayout, "Please enter password", Snackbar.LENGTH_SHORT).show();

                    //return;
                }

                //final AlertDialog waitingDialog = new SpotsDialog(MainActivity.this);
                // waitingDialog.dismiss();


                //Login
                auth.signInWithEmailAndPassword(edtEmail.getText().toString().trim(), edtSenha.getText().toString().trim())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                //  waitingDialog.dismiss();
                                startActivity(new Intent(MainActivity.this, DriverMap.class)); //Welcome //MapsActivity
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // waitingDialog.dismiss();
                        Snackbar.make(rootLayout, R.string.tv_023 + e.getMessage(), Snackbar.LENGTH_SHORT).show();

                        //Active button
                        btnSignInCustomer.setEnabled(true);
                    }
                });

            }
        });

        dialog.setNegativeButton(R.string.tv_024, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        dialog.show();
    }

    /****=========Registra Driver==================================================*****/
    private void driverRegister() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("REGISTER DRIVER");
        dialog.setMessage("Please use email to register");

        LayoutInflater inflater = LayoutInflater.from(this);
        View register_layout = inflater.inflate(R.layout.layout_register, null);

        final MaterialEditText edtEmail = register_layout.findViewById(R.id.edtEmail);
        final MaterialEditText edtSenha = register_layout.findViewById(R.id.edtSenha);
        final MaterialEditText edtConfirmarSenha = register_layout.findViewById(R.id.edtConfirmarSenha);
        final MaterialEditText edtNome = register_layout.findViewById(R.id.edtNome);
        final MaterialEditText edtPhone = register_layout.findViewById(R.id.edtPhone);

        dialog.setView(register_layout);

//        //Set Button
//        dialog.setPositiveButton("REGISTER", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int which) {
//                dialogInterface.dismiss();
//
//                //Check Validation
//                if (edtEmail.getText().toString().trim().isEmpty()) {
//                    Snackbar.make(rootLayout, "Please enter email address", Snackbar.LENGTH_SHORT).show();
//
//                    //return;
//                }
//                if (TextUtils.isEmpty(edtSenha.getText().toString().trim())) {
//                    Snackbar.make(rootLayout, "Please enter password", Snackbar.LENGTH_SHORT).show();
//
//                    //return;
//                }
//                // if ((edtSenha.getText().toString().length() < 4)) {
//                //Snackbar.make(rootLayout, "Passwprd too short!!!", Snackbar.LENGTH_SHORT).show();
//
//                //return;
//                // }
//                if (TextUtils.isEmpty(edtConfirmarSenha.getText().toString().trim())) {
//                    Snackbar.make(rootLayout, "Please confirm password", Snackbar.LENGTH_SHORT).show();
//
//                    //return;
//                }
//                if (TextUtils.isEmpty(edtPhone.getText().toString().trim())) {
//                    Snackbar.make(rootLayout, "Please enter phone number", Snackbar.LENGTH_SHORT).show();
//
//                    //return;
//                }
//                if (TextUtils.isEmpty(edtNome.getText().toString().trim())) {
//                    Snackbar.make(rootLayout, "Please enter name", Snackbar.LENGTH_SHORT).show();
//                    //return;
//                } else if (edtSenha.getText().toString().equals(edtConfirmarSenha.getText().toString())) {
//                    //if (edtSenha.getText().toString().trim()== (edtConfirmarSenha.getText().toString().trim())) {
//                    //  Snackbar.make(rootLayout, "Different password", Snackbar.LENGTH_SHORT).show();
//                    motorista = new EDriver();
//                    motorista.setEmail(edtEmail.getText().toString().trim());
//                    motorista.setPassword(edtSenha.getText().toString().trim());
//                    motorista.setName(edtNome.getText().toString().trim());
//                    motorista.setPhone(edtPhone.getText().toString().trim());
//
//                    //Register new user
//                    autenticacao = ConfigFirebase.getFirebaseAutenticacao();
//                    autenticacao.createUserWithEmailAndPassword(motorista.getEmail(), motorista.getPassword())
//                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                                @Override
//                                public void onSuccess(AuthResult authResult) {
//
//                                    motorista.setIdDriver(String.valueOf(autenticacao.getUid()));
//                                    motorista.setDriver(true);
//                                    motorista.salvar(); //Salva infoCadastro2
//                                    motoristas.child("users").child("drivers").child(String.valueOf(autenticacao.getUid())).child("infoCadastro1")
//                                            .setValue(motorista)
//                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void aVoid) {
//                                                    Snackbar.make(rootLayout, R.string.tv_022, Snackbar.LENGTH_SHORT).show();
//                                                }
//                                            })
//                                            .addOnFailureListener(addOnFailureListenernew OnFailureListener() {
//                                                @Override
//                                                public void onFailure(@NonNull Exception e) {
//                                                    Snackbar.make(rootLayout, R.string.tv_023 + e.getMessage(), Snackbar.LENGTH_SHORT).show();
//                                                }
//                                            });
//
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Snackbar.make(rootLayout, R.string.tv_023 + e.getMessage(), Snackbar.LENGTH_SHORT).show();
//                                }
//                            });
//                    // }
//
//                } else {
//                    Snackbar.make(rootLayout, R.string.tv_037 , Snackbar.LENGTH_SHORT).show();
//                }
//            }
//        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        dialog.show();

    }

    /****=========Login Caroneiro==================================================*****/
    private void customerSignIn() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("SIGN IN CUSTOMER");
        dialog.setMessage("Please use email to sign in");

        LayoutInflater inflater = LayoutInflater.from(this);
        View login_layout = inflater.inflate(R.layout.layout_login, null);

        final MaterialEditText edtEmail = login_layout.findViewById(R.id.edtEmail);
        final MaterialEditText edtSenha = login_layout.findViewById(R.id.edtSenha);

        dialog.setView(login_layout);

        //Dialog setPositiveButton
        dialog.setPositiveButton("SIGN IN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();

                //Set disable button Sign In if is processing
                btnSignInCustomer.setEnabled(false);


                //Check Validation
                if (TextUtils.isEmpty(edtEmail.getText().toString().trim())) {
                    Snackbar.make(rootLayout, "Please enter email address", Snackbar.LENGTH_SHORT).show();

                    return;
                }
                if (TextUtils.isEmpty(edtSenha.getText().toString().trim())) {
                    Snackbar.make(rootLayout, "Please enter password", Snackbar.LENGTH_SHORT).show();

                    return;
                }

                //final AlertDialog waitingDialog = new SpotsDialog(MainActivity.this);
                // waitingDialog.dismiss();


                //Login
                auth.signInWithEmailAndPassword(edtEmail.getText().toString().trim(), edtSenha.getText().toString().trim())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                //  waitingDialog.dismiss();
                                startActivity(new Intent(MainActivity.this, CustomerMap.class)); //Welcome //MapsActivity
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // waitingDialog.dismiss();
                        Snackbar.make(rootLayout, R.string.tv_023 + e.getMessage(), Snackbar.LENGTH_SHORT).show();

                        //Active button
                        btnSignInCustomer.setEnabled(true);
                    }
                });

            }
        });

        dialog.setNegativeButton(R.string.tv_024, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        dialog.show();
    }


    /****=========Registra Caroneiro==================================================*****/
    private void customerRegister() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("REGISTER CUSTOMER");
        dialog.setMessage("Please use email to register");

        LayoutInflater inflater = LayoutInflater.from(this);
        View register_layout = inflater.inflate(R.layout.layout_register, null);

        final MaterialEditText edtEmail = register_layout.findViewById(R.id.edtEmail);
        final MaterialEditText edtSenha = register_layout.findViewById(R.id.edtSenha);
        final MaterialEditText edtConfirmarSenha = register_layout.findViewById(R.id.edtConfirmarSenha);
        final MaterialEditText edtNome = register_layout.findViewById(R.id.edtNome);
        final MaterialEditText edtPhone = register_layout.findViewById(R.id.edtPhone);

        dialog.setView(register_layout);

        //Set Button
        dialog.setPositiveButton("REGISTER", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();

              //Check Validation
                if (edtEmail.getText().toString().trim().isEmpty()) {
                    Snackbar.make(rootLayout, "Please enter email address", Snackbar.LENGTH_SHORT).show();

                    //return;
                }
                if (TextUtils.isEmpty(edtSenha.getText().toString().trim())) {
                    Snackbar.make(rootLayout, "Please enter password", Snackbar.LENGTH_SHORT).show();

                    //return;
                }
                // if ((edtSenha.getText().toString().length() < 4)) {
                //Snackbar.make(rootLayout, "Passwprd too short!!!", Snackbar.LENGTH_SHORT).show();

                //return;
                // }
                if (TextUtils.isEmpty(edtConfirmarSenha.getText().toString().trim())) {
                    Snackbar.make(rootLayout, "Please confirm password", Snackbar.LENGTH_SHORT).show();

                    //return;
                }
                if (TextUtils.isEmpty(edtPhone.getText().toString().trim())) {
                    Snackbar.make(rootLayout, "Please enter phone number", Snackbar.LENGTH_SHORT).show();

                    //return;
                }
                if (TextUtils.isEmpty(edtNome.getText().toString().trim())) {
                    Snackbar.make(rootLayout, "Please enter name", Snackbar.LENGTH_SHORT).show();
                    //return;
                } else if (edtSenha.getText().toString().equals(edtConfirmarSenha.getText().toString())) {
                    //if (edtSenha.getText().toString().trim()== (edtConfirmarSenha.getText().toString().trim())) {
                    //  Snackbar.make(rootLayout, "Different password", Snackbar.LENGTH_SHORT).show();
                    caroneiro = new ECustomer();
                    caroneiro.setEmail(edtEmail.getText().toString().trim());
                    caroneiro.setPassword(edtSenha.getText().toString().trim());
                    caroneiro.setName(edtNome.getText().toString().trim());
                    caroneiro.setPhone(edtPhone.getText().toString().trim());
                    caroneiro.setCustomer(true);

                    //Register new user
                    autenticacao = ConfigFirebase.getFirebaseAutenticacao();
                    autenticacao.createUserWithEmailAndPassword(
                            caroneiro.getEmail(),
                            caroneiro.getPassword()
                    ).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    caroneiro.setIdCustomer(String.valueOf(autenticacao.getUid()));
                                    caroneiro.salvar(); //Salva infoCadastro2
                                    caroneiros.child("users").child("customers").child(String.valueOf(autenticacao.getUid())).child("infoCadastro1")
                                            .setValue(caroneiro)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Snackbar.make(rootLayout, R.string.tv_022, Snackbar.LENGTH_LONG).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Snackbar.make(rootLayout, R.string.tv_023 + e.getMessage(), Snackbar.LENGTH_LONG).show();
                                                }
                                            });

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Snackbar.make(rootLayout, R.string.tv_023 + e.getMessage(), Snackbar.LENGTH_LONG).show();
                                }
                            });
                    // }

                } else {
                    Snackbar.make(rootLayout, R.string.tv_037 , Snackbar.LENGTH_LONG).show();
                }
            }
        });

        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
            }
        });

        dialog.show();
    }


/****=========Verifica se o Driver está logado==================================================*****/
    private void verifyDriverLogged() {
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(MainActivity.this, DriverMap.class); //MapsActivity
                    startActivity(intent);
                    finish();
                } else { //Se ele for igual a nulo o usuario nem precisa saber que se fez o teste por isso comentei
                   // Toast.makeText(MainActivity.this, R.string.tv_036, Toast.LENGTH_LONG).show();
                    Snackbar.make(rootLayout, R.string.tv_036, Snackbar.LENGTH_SHORT).show();
                }
            }
        };
    }


    /****=========Verifica se o Customer está logado==================================================*****/
    private void verifyCustomerLogged() {
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(MainActivity.this, CustomerMap.class); //MapsActivity
                    startActivity(intent);
                    finish();
                } else { //Se ele for igual a nulo o usuario nem precisa saber que se fez o teste por isso comentei
                    // Toast.makeText(MainActivity.this, R.string.tv_036, Toast.LENGTH_LONG).show();
                    Snackbar.make(rootLayout, R.string.tv_036, Snackbar.LENGTH_SHORT).show();
                }
            }
        };
    }

    /****=========Verifica se o Driver está logado==================================================*****/
    private void verifyUserLogged() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            if(motorista.isDriver()){
                Intent intent = new Intent(this, DriverMap.class); //MapsActivity
                startActivity(intent);
                finish();
            } else if(caroneiro.isCustomer()){
                Intent intent = new Intent(this, CustomerMap.class); //MapsActivity
                startActivity(intent);
                finish();
            }

        } else { //Se ele for igual a nulo o usuario nem precisa saber que se fez o teste por isso comentei
            //Toast.makeText(MainActivity.this, R.string.tv_036, Toast.LENGTH_LONG).show();
            Snackbar.make(rootLayout, R.string.tv_036, Snackbar.LENGTH_SHORT).show();
        }
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        auth.addAuthStateListener(firebaseAuthListener);
//    }
//    @Override
//    protected void onStop() {
//        super.onStop();
//        auth.removeAuthStateListener(firebaseAuthListener);
//    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        autenticacao.addAuthStateListener(firebaseAuthListener);
//    }
//    @Override
//    protected void onStop() {
//        super.onStop();
//        autenticacao.removeAuthStateListener(firebaseAuthListener);
//    }

}
