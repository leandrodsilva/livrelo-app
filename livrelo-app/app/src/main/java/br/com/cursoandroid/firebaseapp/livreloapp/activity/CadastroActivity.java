package br.com.cursoandroid.firebaseapp.livreloapp.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import br.com.cursoandroid.firebaseapp.livreloapp.R;
import br.com.cursoandroid.firebaseapp.livreloapp.config.ConfiguracaoFirebase;
import br.com.cursoandroid.firebaseapp.livreloapp.model.Usuario;

public class CadastroActivity extends AppCompatActivity {

    private EditText campoNome, campoEmail, campoTelefone, campoSenha;
    private Button botaoCadastrar;
    private FirebaseAuth autenticacao;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        getSupportActionBar().hide();

        campoNome = findViewById(R.id.cadastro_nome);
        campoEmail = findViewById(R.id.cadastro_email);
        campoTelefone = findViewById(R.id.cadastro_telefone);
        campoSenha = findViewById(R.id.cadastro_senha);
        botaoCadastrar = findViewById(R.id.cadastro_botao);

        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoNome = campoNome.getText().toString();
                String textoEmail = campoEmail.getText().toString();
                String textoTelefone = campoTelefone.getText().toString();
                String textoSenha = campoSenha.getText().toString();

                //valida se campos foram preenchidos
                if (!textoNome.isEmpty()) {
                    if (!textoEmail.isEmpty()) {
                        if (!textoTelefone.isEmpty()) {
                            if (!textoSenha.isEmpty()) {
                                usuario = new Usuario();
                                usuario.setNome(textoNome);
                                usuario.setEmail(textoEmail);
                                usuario.setTelefone(textoTelefone);
                                usuario.setSenha(textoSenha);

                                cadastrarUsuario();

                            } else {
                                Toast.makeText(CadastroActivity.this, "Necessário preencher senha!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CadastroActivity.this, "Necessário preencher telefone", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CadastroActivity.this, "Necessário preencher email", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(CadastroActivity.this, "Necessário preencher nome", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void cadastrarUsuario() {
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuario.getEmail(), usuario.getSenha())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                finish();
                            } else {
                                String excecao = "";
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthWeakPasswordException e) {
                                    excecao = "Digite uma senha mais forte";
                                } catch (FirebaseAuthInvalidCredentialsException e) {
                                    excecao = "Digite um e-amil válido";
                                } catch (FirebaseAuthUserCollisionException e) {
                                    excecao = "Esta conta já existe";
                                } catch (Exception e) {
                                    excecao = "Erro ao cadastrar usuário: " + e.getMessage();
                                    e.printStackTrace();
                                }
                                Toast.makeText(CadastroActivity.this, excecao, Toast.LENGTH_SHORT).show();
                            }
                        }
        });
    }
}
