package com.example.ar.Runtime3Dmodel;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import com.example.ar.R;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.ArFragment;

public class Runtime3Dmodel extends AppCompatActivity {
    ArFragment arFragment;
    String Assest_3D="http://20e1529a73db.ngrok.io/Andy.gltf";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runtime3_dmodel);
        arFragment= (ArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        arFragment.setOnTapArPlaneListener(((hitResult, plane, motionEvent) -> {
            Anchor anchor=hitResult.createAnchor();
            placeobject(anchor);
        }));

    }

    private void placeobject(Anchor anchor) {
        ModelRenderable.builder().setSource(this, RenderableSource.builder().
                setSource(this, Uri.parse(Assest_3D),RenderableSource.SourceType.GLTF2)
                .setScale(.75f).setRecenterMode(RenderableSource.RecenterMode.ROOT).build())
                .setRegistryId(Assest_3D)
                .build().thenAccept(modelRenderable -> _addnodetoScene(modelRenderable,anchor))
                .exceptionally(throwable -> {
                    AlertDialog.Builder builder=new AlertDialog.Builder(this);
                    builder.setMessage(throwable.getMessage()).show();
                    return null;
                }
                );
    }

    private void _addnodetoScene(ModelRenderable modelRenderable,Anchor anchor) {
        AnchorNode anchorNode=new AnchorNode(anchor);
        anchorNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);

    }
}