package com.example.ar.ModelAnimation;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import com.example.ar.R;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.SkeletonNode;
import com.google.ar.sceneform.animation.ModelAnimator;
import com.google.ar.sceneform.rendering.AnimationData;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;

public class ModelAnimation extends AppCompatActivity {
    ArFragment arFragment;
    Button button;
    ModelAnimator animator;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_model_animation);
        button=findViewById(R.id.animate);
        arFragment= (ArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        assert arFragment != null;
        arFragment.setOnTapArPlaneListener(((hitResult, plane, motionEvent) -> {
            Anchor anchor =hitResult.createAnchor();
            ModelRenderable.builder().setSource(this, Uri.parse("53_SkeletonSystem_LODa.sfb"))
                    .build().thenAccept(modelRenderable -> {
                        _Createmodel(anchor,modelRenderable);
            });
        }));
    }

    private void _Createmodel(Anchor anchor, ModelRenderable modelRenderable) {
        AnchorNode anchorNode=new AnchorNode(anchor);
        SkeletonNode skeletonNode=new SkeletonNode();
        skeletonNode.setParent(anchorNode);
        skeletonNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        button.setOnClickListener(v -> {
            _animatemodel(modelRenderable);
        });
    }

    private void _animatemodel(ModelRenderable modelRenderable) {
        if(animator!=null && animator.isRunning())
            animator.end();
        int animatorcount=modelRenderable.getAnimationDataCount();
        if(i==animatorcount)
            i=0;
        AnimationData animationData=modelRenderable.getAnimationData(i);
        animator=new ModelAnimator(animationData,modelRenderable);
        animator.start();


    }
}