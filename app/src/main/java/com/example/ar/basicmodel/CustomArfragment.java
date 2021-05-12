package com.example.ar.basicmodel;
import com.example.ar.basicmodel.MainActivity;
import com.google.ar.core.Config;
import com.google.ar.core.Session;
import com.google.ar.sceneform.ux.ArFragment;
import java.util.Objects;

public class CustomArfragment extends ArFragment {
    @Override
    protected Config getSessionConfiguration(Session session) {
        Config config=new Config(session);
        config.setUpdateMode(Config.UpdateMode.LATEST_CAMERA_IMAGE);
        config.setFocusMode(Config.FocusMode.AUTO);
        session.configure(config);
        this.getArSceneView().getScene();
        ((MainActivity) Objects.requireNonNull(getActivity())).setupdatabase(config,session);
        return config;
    }
}
