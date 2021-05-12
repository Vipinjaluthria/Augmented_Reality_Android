package com.example.ar.basicmodel;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import com.example.ar.R;
import com.google.ar.core.Anchor;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Frame;
import com.google.ar.core.Session;
import com.google.ar.core.TrackingState;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Scene;
import com.google.ar.sceneform.rendering.ModelRenderable;

import java.util.Collection;

public class MainActivity extends AppCompatActivity implements Scene.OnUpdateListener{
    CustomArfragment customArfragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customArfragment = (CustomArfragment) getSupportFragmentManager().findFragmentById(R.id.fragment);




        /*fragment= (ArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        assert fragment != null;*/
       /* fragment.setOnTapArPlaneListener(((hitResult, plane, motionEvent) -> {
            Anchor anchor=hitResult.createAnchor();

            ModelRenderable.builder().setSource(this, Uri.parse("model.sfb"))
                    .build().thenAccept(modelRenderable -> addmodeltoscene(anchor,modelRenderable))
                    .exceptionally(throwable -> {
                        AlertDialog.Builder alertdialog = new AlertDialog.Builder(this);
                        alertdialog.setMessage(throwable.getMessage()).show();
                        return null;
                    });
        }));
    }
    private void addmodeltoscene(Anchor anchor, ModelRenderable modelRenderable) {
        AnchorNode anchorNode=new AnchorNode(anchor);
        TransformableNode transformableNode=new TransformableNode(fragment.getTransformationSystem());
        transformableNode.setParent(anchorNode);
        transformableNode.setRenderable(modelRenderable);
        fragment.getArSceneView().getScene().addChild(anchorNode);
        transformableNode.select();

    }*/
    }
   public void setupdatabase(Config config, Session session)
   {
       Bitmap Foxbitmap= BitmapFactory.decodeResource(getResources(),R.drawable.fox);
       AugmentedImageDatabase imageDatabase=new AugmentedImageDatabase(session);
       imageDatabase.addImage("Foxbit",Foxbitmap);
       config.setAugmentedImageDatabase(imageDatabase);
   }

    @Override
    public void onUpdate(FrameTime frameTime) {
        Frame frame=customArfragment.getArSceneView().getArFrame();
        Collection<AugmentedImage> augmentedImages=frame.getUpdatedTrackables(AugmentedImage.class);
        for (AugmentedImage augmentedImage:augmentedImages) {
            if(augmentedImage.getTrackingState()== TrackingState.TRACKING)
            {
                if(augmentedImage.getName().equals("Foxbit"))
                {
                    Anchor anchor =augmentedImage.createAnchor(augmentedImage.getCenterPose());
                    CreateModel(anchor);
                }
            }

        }
    }

    private void CreateModel(Anchor anchor) {
        ModelRenderable.builder().setSource(this, Uri.parse("ArcticFox_Posted.sfb"))
                .build().thenAccept(modelRenderable -> _createmodel(modelRenderable,anchor)  );
    }

    private void _createmodel(ModelRenderable modelRenderable, Anchor anchor) {
        AnchorNode anchorNode=new AnchorNode(anchor);
        anchorNode.setRenderable(modelRenderable);
        customArfragment.getArSceneView().getScene().addChild(anchorNode);
    }
}
