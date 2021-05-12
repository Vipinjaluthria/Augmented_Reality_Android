package com.example.ar.Playvideo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

import com.example.ar.R;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.ExternalTexture;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

public class Playvideo extends AppCompatActivity {
    ArFragment arFragment;
    ExternalTexture externalTexture;
    MediaPlayer mediaPlayer;
    float HEIGHT=1.25f;
    private static final Color CHROMA_KEY_COLOR = new Color(0.1843f, 1.0f, 0.098f);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playvideo);
        arFragment= (ArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        mediaPlayer=MediaPlayer.create(this,R.raw.shinchan);
        externalTexture =new ExternalTexture();
        mediaPlayer.setSurface(externalTexture.getSurface());
        mediaPlayer.setLooping(true);
        arFragment.setOnTapArPlaneListener(((hitResult, plane, motionEvent) -> {
            Anchor anchor=hitResult.createAnchor();
            ModelRenderable.builder().setSource(this,R.raw.video)
                    .build().thenAccept(modelRenderable -> _createmodel(anchor,modelRenderable));
        }));
    }
    private void _createmodel(Anchor anchor, ModelRenderable modelRenderable) {
        AnchorNode anchorNode=new AnchorNode(anchor);
        modelRenderable.getMaterial().setExternalTexture("videoTexture",externalTexture);
        //modelRenderable.getMaterial().setFloat4("keyColor",CHROMA_KEY_COLOR);
        if(!mediaPlayer.isPlaying())
        {
            mediaPlayer.start();
            externalTexture.getSurfaceTexture().setOnFrameAvailableListener(surfaceTexture -> {
                anchorNode.setRenderable(modelRenderable);
                externalTexture.getSurfaceTexture().setOnFrameAvailableListener(null);
            });
        }
        else
            anchorNode.setRenderable(modelRenderable);
            float width=mediaPlayer.getVideoWidth();
            float height=mediaPlayer.getVideoHeight();
            anchorNode.setLocalScale(new Vector3(HEIGHT*(width/height),HEIGHT,1.0f));

        arFragment.getArSceneView().getScene().addChild(anchorNode);

    }
}