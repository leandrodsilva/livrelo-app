package br.com.cursoandroid.firebaseapp.livreloapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.cursoandroid.firebaseapp.livreloapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CadastroFragment extends Fragment {

    public CadastroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cadastro, container, false);
    }

}
