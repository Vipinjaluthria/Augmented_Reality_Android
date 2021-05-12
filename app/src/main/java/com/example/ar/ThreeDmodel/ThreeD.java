package com.example.ar.ThreeDmodel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.ar.R;
import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.ArFragment;

public class ThreeD extends AppCompatActivity {
    ArFragment arFragment;
    Button Sphere,cylinder,Cube;
    private enum Shapetype{
        CUBE,
        SPHERE,
        CYLINDER,
    }
    private Shapetype showtype=Shapetype.CUBE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_d);
        Sphere=findViewById(R.id.sphere);
        cylinder=findViewById(R.id.cylinder);
        Cube=findViewById(R.id.cube);
        arFragment= (ArFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        arFragment.setOnTapArPlaneListener(((hitResult, plane, motionEvent) -> {
            Anchor anchor=hitResult.createAnchor();
            if(showtype==Shapetype.CUBE)
            { placecube(anchor);

            }
            else if(showtype==Shapetype.CYLINDER)
                placecylinder(anchor);
            else
                placesphere(anchor);
        }));
        Cube.setOnClickListener(view->{
            showtype=Shapetype.CUBE;
        });
        Sphere.setOnClickListener(view->{
            showtype=Shapetype.SPHERE;
        });
        cylinder.setOnClickListener(view->{
            showtype=Shapetype.CYLINDER;
        });
    }

    private void placesphere(Anchor anchor) {
        MaterialFactory.makeOpaqueWithColor(this,new Color(android.graphics.Color.BLUE))
                .thenAccept(material ->
                        {
                ModelRenderable modelRenderable= ShapeFactory.makeCube(new Vector3(0.1f,0.1f,0.1f),new Vector3(0,0.1f,0),material);
                  placemodel(modelRenderable,anchor);
    });}

    private void placecylinder(Anchor anchor) {
        MaterialFactory.makeOpaqueWithColor(this,new Color(android.graphics.Color.BLUE))
                .thenAccept(material ->
                {
                    ModelRenderable modelRenderable= ShapeFactory.makeCylinder(0.2f,0.2f,new Vector3(0,0.2f,0),material);
                    placemodel(modelRenderable,anchor);
                });
    }

    private void placecube(Anchor anchor) {
        MaterialFactory.makeOpaqueWithColor(this,new Color(android.graphics.Color.BLUE))
                .thenAccept(material ->
                {
                    ModelRenderable modelRenderable= ShapeFactory.makeSphere(0.2f,new Vector3(0,0.1f,0),material);
                    placemodel(modelRenderable,anchor);
                });



    }

    private void placemodel(ModelRenderable modelRenderable, Anchor anchor) {
        AnchorNode anchorNode=new AnchorNode(anchor);
        anchorNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
    }
}