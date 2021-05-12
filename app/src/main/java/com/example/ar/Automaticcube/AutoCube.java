package com.example.ar.Automaticcube;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ar.R;
import com.google.ar.core.Anchor;
import com.google.ar.core.Frame;
import com.google.ar.core.Plane;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.Collection;

public class AutoCube extends AppCompatActivity {
    boolean isModel=false;
    ArFragment arFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_cube);
        arFragment= (ArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        assert arFragment != null;
        arFragment.getArSceneView().getScene().addOnUpdateListener(frameTime -> {
            if(isModel)
            return;
            Frame frame=arFragment.getArSceneView().getArFrame();
            Collection<Plane> planes=frame.getUpdatedTrackables(Plane.class);
            for ( Plane plane:planes){
                if(plane.getTrackingState()== TrackingState.TRACKING) {
                    Anchor anchor = plane.createAnchor(plane.getCenterPose());

                    makecube(anchor);
                    break;
                }

            }

        });

    }

    private void makecube(Anchor anchor) {
        isModel=true;
        MaterialFactory.makeOpaqueWithColor(this,new Color(android.graphics.Color.GREEN))
                .thenAccept(material -> {
                    ModelRenderable modelRenderable= ShapeFactory.makeCube(new Vector3(0.3f,0.3f,0.3f),new Vector3(0f,0.3f,0f),material);
                    AnchorNode anchorNode=new AnchorNode(anchor);
                    anchorNode.setRenderable(modelRenderable);
                    arFragment.getArSceneView().getScene().addChild(anchorNode);

                });
    }
}