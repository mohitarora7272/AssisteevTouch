package com.touchpro.controller;

import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
@SuppressWarnings("all")
public class FlashlightController {
    private Camera camera;
    private boolean isFlashOn;
    private Camera.Parameters params;

    public FlashlightController() {
        super();
        this.isFlashOn = false;
    }

    private void getCamera() {
        if (this.camera != null || this.camera != null) {
            return;
        }
        try {
            this.camera = Camera.open();
            this.params = this.camera.getParameters();
            this.camera.startPreview();
        } catch (RuntimeException ex) {
        }
    }

    public boolean isFlashOn() {
        return this.isFlashOn;
    }

    public void release() {
        if (this.camera == null) {
            return;
        }
        while (true) {
            try {
                this.camera.stopPreview();
                this.camera.release();
                this.camera = null;
            } catch (Exception ex) {
                continue;
            }
            break;
        }
    }

    public void setFlashOn(final boolean isFlashOn) {
        this.isFlashOn = isFlashOn;
    }

    public void setFlashlight(final boolean b) {
        if (b) {
            this.setFlashOn(true);
            new TurnOnFlashlight().execute(new Void[0]);
            return;
        }
        this.setFlashOn(false);
        new TurnOffFlashlight().execute(new Void[0]);
    }

    public void turnOffFlash() {
        if (this.camera == null || this.params == null) {
            return;
        }
        this.params.setFlashMode("off");
        this.camera.setParameters(this.params);
        this.camera.stopPreview();
    }

    public void turnOnFlash() {
        if (this.camera == null || this.params == null) {
            return;
        }
        this.params.setFlashMode("torch");
        this.camera.setParameters(this.params);

        if (Build.VERSION.SDK_INT >= 21) {
            SurfaceTexture mPreviewTexture = new SurfaceTexture(0);
            try {
                this.camera.setPreviewTexture(mPreviewTexture);
            } catch (Exception ex) {
                // Ignore
            }
        }

        this.camera.startPreview();


    }

    private class TurnOffFlashlight extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(final Void void1) {
            super.onPostExecute(void1);
            FlashlightController.this.turnOffFlash();
            FlashlightController.this.release();
            Log.d("TEST", "Task ON Flashlight");
        }
    }

    private class TurnOnFlashlight extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(final Void[] array) {
            Log.d("TEST", "Task ON Flashlight");
            FlashlightController.this.getCamera();
            return null;
        }


        @Override
        protected void onPostExecute(final Void void1) {
            super.onPostExecute(void1);
            FlashlightController.this.turnOnFlash();
        }
    }
}