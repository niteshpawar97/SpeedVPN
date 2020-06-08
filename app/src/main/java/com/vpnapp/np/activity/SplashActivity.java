package com.vpnapp.np.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.vpnapp.np.BuildConfig;
import com.vpnapp.np.Preference;
import com.vpnapp.np.R;
import com.vpnapp.np.utils.AdModFacebook;
import com.vpnapp.np.utils.Constant;
import com.vpnapp.np.utils.NetworkState;

import java.util.Timer;
import java.util.TimerTask;

import static com.vpnapp.np.utils.Constant.APP_IN_PURCHASE_KEY;
import static com.vpnapp.np.utils.Constant.MONTHLY_SUB;
import static com.vpnapp.np.utils.Constant.SIX_MONTHS_SUB;
import static com.vpnapp.np.utils.Constant.YEARLY_SUB;

public class SplashActivity extends AppCompatActivity {

    Preference preference;
     InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        PackageManager packageManager = this.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        TextView version = findViewById(R.id.Version);
        version.setText(getString(R.string.version) + packageInfo.versionName);

        preference = new Preference(SplashActivity.this);
        preference.setStringpreference(APP_IN_PURCHASE_KEY, BuildConfig.IN_APPKEY);
        preference.setStringpreference(MONTHLY_SUB, BuildConfig.MONTHLY);
        preference.setStringpreference(SIX_MONTHS_SUB, BuildConfig.SIX_MONTH);
        preference.setStringpreference(YEARLY_SUB, BuildConfig.YEARLY);

        Preference preference = new Preference(getApplicationContext());
        if (!preference.isBooleenPreference(Constant.PRIMIUM_STATE)) {
            /*if (BuildConfig.FACEBOOK_AD) {
                AdModFacebook.buildAdFullScreen(getApplicationContext(), new AdModFacebook.MyAdListener() {
                    @Override
                    public void onAdClicked() {
                    }

                    @Override
                    public void onAdClosed() {
                        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(myIntent);
                        finish();
                        // Code to be executed when the interstitial ad is closed.
                    }

                    @Override
                    public void onAdLoaded() {
                    }

                    @Override
                    public void onAdOpened() {
                    }

                    @Override
                    public void onFaildToLoad(AdError adError) {
                        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(myIntent);
                        finish();
                        // Code to be executed when an ad request fails.
                    }

                    @Override
                    public void onInterstitialDismissed() {
                    }

                    @Override
                    public void onInterstitialDisplayed() {
                    }

                    @Override
                    public void onLoggingImpression() {
                    }
                });
            }
            /*
            mInterstitialAd = new InterstitialAd(this);
            mInterstitialAd.setAdUnitId(BuildConfig.GOOGLE_INTERSTITIAL);
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(myIntent);
                    finish();
                    // Code to be executed when an ad request fails.
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when the ad is displayed.
                }

                @Override
                public void onAdClicked() {
                    // Code to be executed when the user clicks on an ad.
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }

                @Override
                public void onAdClosed() {
                    Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(myIntent);
                    finish();
                    // Code to be executed when the interstitial ad is closed.
                }
            });*/

        }
        if (NetworkState.isOnline(this)) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override public void run() {
                            /*if (mInterstitialAd.isLoaded()) {
                                mInterstitialAd.show();
                            } else
                                */ {
                                Log.d("TAG", "The interstitial wasn't loaded yet.");
                                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(myIntent);
                                finish();
                            }
                        }
                    });


                }
            }, 3000);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getString(R.string.network_error))
                    .setMessage(getString(R.string.network_error_message))
                    .setNegativeButton(getString(R.string.ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    onBackPressed();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
