//package com.optiflowx.applekeyboard;
//
//package com.arvin.applekeyboard;
//
//import android.content.Context;
//import android.content.Intent;
//import android.inputmethodservice.InputMethodService;
//import android.media.AudioManager;
//import android.os.AsyncTask;
//import android.os.Vibrator;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.inputmethod.EditorInfo;
//import android.view.inputmethod.InputConnection;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import androidx.constraintlayout.widget.ConstraintLayout;
//import androidx.viewpager.widget.ViewPager;
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//import butterknife.OnTouch;
//import butterknife.Optional;
//import com.arvin.applekeyboard.adapter.MyEmojiPagerAdapter;
//import com.arvin.applekeyboard.model.EmojiPage;
//import com.karumi.dexter.BuildConfig;
//import java.util.ArrayList;
//import java.util.List;
//
//public class AppleKeyboardIME extends InputMethodService {
//    private boolean DkEqaUxxlOsNuQD = true;
//    private boolean XfaXQGfGvYYcmfw = true;
//    private boolean ioDvLIaGvJsLPDs = false;
//
//    ImageView ivAnimals;
//
//    ImageView ivCaps;
//
//    ImageView ivFood;
//
//    ImageView ivRecent;
//
//    ImageView ivSmiles;
//
//    ImageView ivSymbol;
//
//    ImageView ivTravel;
//    private boolean jAUIhSqsKFgrqTN = true;
//
//    RelativeLayout keyCaps;
//
//    LinearLayout layoutSug1;
//
//    LinearLayout layoutSug2;
//
//    LinearLayout layoutSug3;
//    private int currentEmojiViewPage;
//
//    /* renamed from: currentEmojiViewPage  reason: collision with other field name */
//    private EditorInfo editorInfo;
//
//    /* renamed from: currentEmojiViewPage  reason: collision with other field name */
//    private ConstraintLayout cLayout;
//
//    /* renamed from: currentEmojiViewPage  reason: collision with other field name */
//    private KeyboardType keyboardType = KeyboardType.QWERT;
//
//    /* renamed from: currentEmojiViewPage  reason: collision with other field name */
//    private MyKeyboardView kView;
//
//    /* renamed from: currentEmojiViewPage  reason: collision with other field name */
////  TODO(WHat is this?)
//    private EmojiViewPageModel f3715currentEmojiViewPage;
//
//    /* renamed from: currentEmojiViewPage  reason: collision with other field name */
//    private EmojiViewPageModel f3716currentEmojiViewPage = new tKYPUSPhLJoTsAs();
//
//    /* renamed from: currentEmojiViewPage  reason: collision with other field name */
//    private int[] f3717currentEmojiViewPage = {R.id.keyQ, R.id.keyW, R.id.keyE, R.id.keyR, R.id.keyT, R.id.keyY, R.id.keyU, R.id.keyI, R.id.keyO, R.id.keyP, R.id.keyA, R.id.keyS, R.id.keyD, R.id.keyF, R.id.keyG, R.id.keyH, R.id.keyJ, R.id.keyK, R.id.keyL, R.id.keyZ, R.id.keyX, R.id.keyC, R.id.keyV, R.id.keyB, R.id.keyN, R.id.keyM};
//    private boolean pSaDHeqYFoTHViy = false;
//    private int qKxLAWwCNLplDeL;
//    /* access modifiers changed from: private */
//
//    /* renamed from: qKxLAWwCNLplDeL  reason: collision with other field name */
//    public boolean isCapsLock = false;
//    private boolean rFuvgVZhWUgsIPD = true;
//    /* access modifiers changed from: private */
//    public boolean isCaps = true;
//
//    TextView tvActionTxt;
//
//    TextView tvSug1;
//
//    TextView tvSug2;
//
//    TextView tvSug3;
//
//    ViewPager viewPager;
//
//    static class CixuzTvTCLmDHIY {
//        static final int[] currentEmojiViewPage;
//
//        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
//        /* JADX WARNING: Failed to process nested try/catch */
//        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0012 */
//        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001d */
//        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x0028 */
//    }
//
//    private enum KeyType {
//        NORMAL,
//        DELETE,
//        RETURN,
//        SPACE
//    }
//
//    private enum KeyboardType {
//        QWERT,
//        NUMBER_SYMBOL,
//        SYMBOL,
//        PHONE,
//        PHONE_SYMBOLS,
//        NUMBER,
//        EMOJI
//    }
//
//    class EmojiViewPageModel implements ViewPager.ljEFihTXNQrVmxc {
//
//        public void currentEmojiViewPage(int i) {
//            ViewPager viewPager = AppleKeyboardIME.this.viewPager;
//            if (viewPager != null) {
//                int currentItem = viewPager.getCurrentItem();
//                AppleKeyboardIME.this.setEmojiSelectionIcons();
//                if (currentItem == 0) {
//                    AppleKeyboardIME.this.ivRecent.setImageResource(R.mipmap.ic_recent_selected);
//                } else if (currentItem == 1) {
//                    AppleKeyboardIME.this.ivSmiles.setImageResource(R.mipmap.ic_smiles_selected);
//                } else if (currentItem == 2) {
//                    AppleKeyboardIME.this.ivAnimals.setImageResource(R.mipmap.ic_animals_selected);
//                } else if (currentItem == 3) {
//                    AppleKeyboardIME.this.ivFood.setImageResource(R.mipmap.ic_food_selected);
//                } else if (currentItem == 4) {
//                    AppleKeyboardIME.this.ivTravel.setImageResource(R.mipmap.ic_travel_selected);
//                } else if (currentItem == 5) {
//                    AppleKeyboardIME.this.ivSymbol.setImageResource(R.mipmap.ic_symbol_pressed);
//                }
//            }
//        }
//
//        public void isCaps(int i, float f, int i2) {
//        }
//    }
//
//    class tKYPUSPhLJoTsAs extends EmojiViewPageModel {
//        public void currentEmojiViewPage(View view) {
//            boolean unused = AppleKeyboardIME.this.isCapsLock = true;
//            boolean unused2 = AppleKeyboardIME.this.isCaps = true;
//            AppleKeyboardIME.this.setKeyboardViewState();
//        }
//
//        public void qKxLAWwCNLplDeL(View view) {
//            boolean unused = AppleKeyboardIME.this.isCapsLock = false;
//            AppleKeyboardIME appleKeyboardIME = AppleKeyboardIME.this;
//            boolean unused2 = appleKeyboardIME.isCaps = !appleKeyboardIME.isCaps;
//            AppleKeyboardIME.this.setKeyboardViewState();
//        }
//    }
//
//    /* access modifiers changed from: private */
//    public void setKeyboardViewState() {
//        if (this.keyboardType == KeyboardType.QWERT && this.ivCaps != null) {
//            for (int findViewById : this.f3717currentEmojiViewPage) {
//                TextView textView = (TextView) this.kView.findViewById(findViewById);
//                if (this.isCapsLock) {
//                    this.ivCaps.setImageResource(R.mipmap.ic_caps_lock);
//                    textView.setText(textView.getText().toString().toUpperCase());
//                } else if (this.isCaps) {
//                    this.ivCaps.setImageResource(R.drawable.ic_caps);
//                    textView.setText(textView.getText().toString().toUpperCase());
//                } else {
//                    this.ivCaps.setImageResource(R.drawable.ic_simple);
//                    textView.setText(textView.getText().toString().toLowerCase());
//                }
//            }
//        }
//    }
//
//    private void DkEqaUxxlOsNuQD() {
//        this.isCapsLock = false;
//        this.isCaps = true;
//        this.XfaXQGfGvYYcmfw = true;
//        this.jAUIhSqsKFgrqTN = true;
//        this.ioDvLIaGvJsLPDs = false;
//        this.pSaDHeqYFoTHViy = false;
//        this.rFuvgVZhWUgsIPD = true;
//        this.jAUIhSqsKFgrqTN = com.arvin.applekeyboard.mbwdBpBZVHyLGzB.tKYPUSPhLJoTsAs.rFuvgVZhWUgsIPD(this);
//        this.XfaXQGfGvYYcmfw = com.arvin.applekeyboard.mbwdBpBZVHyLGzB.tKYPUSPhLJoTsAs.DkEqaUxxlOsNuQD(this);
//        this.ioDvLIaGvJsLPDs = com.arvin.applekeyboard.mbwdBpBZVHyLGzB.tKYPUSPhLJoTsAs.bQdpaFOxUQJIjDl(this);
//        this.pSaDHeqYFoTHViy = com.arvin.applekeyboard.mbwdBpBZVHyLGzB.tKYPUSPhLJoTsAs.PlOkqYdIftTaSLc(this);
//        this.rFuvgVZhWUgsIPD = com.arvin.applekeyboard.mbwdBpBZVHyLGzB.tKYPUSPhLJoTsAs.getSuggestion(this);
//    }
//
//    public void getSuggestion(List list) {
//        TextView textView;
//        for (int i = 0; i < list.size(); i++) {
//            String str = (String) list.get(i);
//            if (i == 0) {
//                TextView textView2 = this.tvSug1;
//                if (textView2 != null) {
//                    textView2.setText(str);
//                }
//            } else if (i == 1) {
//                TextView textView3 = this.tvSug2;
//                if (textView3 != null) {
//                    textView3.setText(str);
//                }
//            } else if (i == 2 && (textView = this.tvSug3) != null) {
//                textView.setText(str);
//            }
//        }
//    }
//
//    public void bQdpaFOxUQJIjDl() {
//        if (getCurrentInputConnection() != null) {
//            getCurrentInputConnection().sendKeyEvent(new KeyEvent(0, 67));
//            rFuvgVZhWUgsIPD();
//        }
//    }
//
//    private void onVibrate() {
//        try {
//            ((Vibrator) getSystemService("vibrator")).vibrate(50);
//        } catch (Exception unused) {
//        }
//    }
//
//    private void onAudioPlay(KeyType keyType) {
//        AudioManager audioManager = (AudioManager) getSystemService("audio");
//        int i = CixuzTvTCLmDHIY.currentEmojiViewPage[keyType.ordinal()];
//        if (i == 1) {
//            audioManager.playSoundEffect(5);
//        } else if (i == 2) {
//            audioManager.playSoundEffect(6);
//        } else if (i == 3) {
//            audioManager.playSoundEffect(8);
//        } else if (i == 4) {
//            audioManager.playSoundEffect(7);
//        }
//    }
//
//    private void onTextHandle(String str) {
//        try {
//            if (this.keyboardType != KeyboardType.QWERT) {
//                return;
//            }
//            if (!TextUtils.isEmpty(str)) {
//                if (getCurrentInputConnection() != null) {
//                    String[] split = ((String) getCurrentInputConnection().getTextBeforeCursor(50, 0)).trim().split("\\s+");
//                    String str2 = split[split.length - 1];
//                    boolean equals = ((String) getCurrentInputConnection().getTextBeforeCursor(1, 0)).equals(" ");
//                    if (!TextUtils.isEmpty(str2) && !equals) {
//                        getCurrentInputConnection().deleteSurroundingText(str2.length(), 0);
//                    }
//                    String lowerCase = (TextUtils.isEmpty(str2) || Character.isUpperCase(str2.charAt(0))) ? str : str.toLowerCase();
//                    if (equals) {
//                        lowerCase = lowerCase.toLowerCase();
//                    }
//                    String str3 = (String) getCurrentInputConnection().getTextBeforeCursor(2, 0);
//                    if (TextUtils.isEmpty(str3) || !str3.endsWith(". ")) {
//                        str = lowerCase;
//                    }
//                    if (this.isCapsLock) {
//                        str = str.toUpperCase();
//                    }
//                    InputConnection currentInputConnection = getCurrentInputConnection();
//                    currentInputConnection.commitText(str + " ", 0);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void setTextOnInput(String str) {
//        String str2;
//        if (getCurrentInputConnection() != null) {
//            getCurrentInputConnection().commitText(str, 1);
//            if (this.XfaXQGfGvYYcmfw && (str2 = (String) getCurrentInputConnection().getTextBeforeCursor(3, 0)) != null) {
//                if (str2.matches("^\\b.*?\\b  $") || str2.matches("^\\b.*?\\b [.]$")) {
//                    getCurrentInputConnection().deleteSurroundingText(2, 0);
//                    getCurrentInputConnection().commitText(". ", 1);
//                }
//                if (str2.matches("^\\b.*?\\b [,]$")) {
//                    getCurrentInputConnection().deleteSurroundingText(2, 0);
//                    getCurrentInputConnection().commitText(", ", 1);
//                }
//            }
//            if (this.DkEqaUxxlOsNuQD) {
//                rFuvgVZhWUgsIPD();
//            }
//            if (this.isCapsLock) {
//                this.isCaps = true;
//                setKeyboardViewState();
//            } else if (this.jAUIhSqsKFgrqTN) {
//                String str3 = (String) getCurrentInputConnection().getTextBeforeCursor(3, 0);
//                boolean z = this.isCaps;
//                if (TextUtils.isEmpty(str3)) {
//                    this.isCaps = true;
//                } else if (str3.endsWith(". ") || str3.endsWith("\n")) {
//                    this.isCaps = true;
//                } else {
//                    this.isCaps = false;
//                }
//                if (z != this.isCaps) {
//                    setKeyboardViewState();
//                }
//            }
//            if (this.pSaDHeqYFoTHViy) {
//                onAudioPlay(KeyType.NORMAL);
//            }
//            if (this.ioDvLIaGvJsLPDs) {
//                onVibrate();
//            }
//        }
//    }
//
//    private int ioDvLIaGvJsLPDs(View view) {
//        if (view.getParent() == view.getRootView()) {
//            return view.getLeft();
//        }
//        return view.getLeft() + ioDvLIaGvJsLPDs((View) view.getParent());
//    }
//
//    private void setReturnText() {
//        TextView textView = this.tvActionTxt;
//        if (textView != null) {
//            int i = this.currentEmojiViewPage;
//            textView.setText(i == 3 ? "Search" : i == 6 ? "Done" : i == 2 ? "Go" : i == 4 ? "Send" : i == 7 ? "Back" : i == 5 ? "Next" : "return");
//        }
//    }
//
//    private int pSaDHeqYFoTHViy(View view) {
//        if (view.getParent() == view.getRootView()) {
//            return view.getTop();
//        }
//        return view.getTop() + pSaDHeqYFoTHViy((View) view.getParent());
//    }
//
//    /* access modifiers changed from: private */
//    public void setEmojiSelectionIcons() {
//        this.ivRecent.setImageResource(R.mipmap.ic_recent);
//        this.ivSmiles.setImageResource(R.mipmap.ic_smiles);
//        this.ivAnimals.setImageResource(R.mipmap.ic_animals);
//        this.ivFood.setImageResource(R.mipmap.ic_food);
//        this.ivTravel.setImageResource(R.mipmap.ic_travel);
//        this.ivSymbol.setImageResource(R.mipmap.ic_symbol);
//    }
//
//    private void rFuvgVZhWUgsIPD() {
//        try {
//            if (this.keyboardType == KeyboardType.QWERT && getCurrentInputConnection() != null) {
//                String valueOf = String.valueOf(getCurrentInputConnection().getTextBeforeCursor(50, 0));
//                if (!TextUtils.isEmpty(valueOf)) {
//                    String[] split = valueOf.trim().split("\\s+");
//                    String str = split[split.length - 1];
//                    new tKYPUSPhLJoTsAs(new EmojiViewPageModel(this)).execute(new Object[]{getApplicationContext(), str});
//                    if (str != null && str.length() >= 2) {
//                        currentEmojiViewPage = str.substring(0, str.length() - 1);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void pKQrvliEoIcfviL(GYHOWQSyDKGpNuK.cUdJPWMmEiEWIka.cUdJPWMmEiEWIka.cUdJPWMmEiEWIka.PMXTRzBEixJZbEu.EmojiViewPageModel jOgcKJVTGOIrhLr) {
//        setTextOnInput(jOgcKJVTGOIrhLr.XfaXQGfGvYYcmfw());
//    }
//
//    public boolean moveDatabaseFrom(Context context, String str) {
//        return super.moveDatabaseFrom(context, str);
//    }
//
//
//
//    public void onActionClicked(View view) {
//        try {
//            getCurrentInputConnection().sendKeyEvent(new KeyEvent(0, 66));
//            if ((this.editorInfo.imeOptions & 1073742079) == 5) {
//                Log.d("KP2AK", "action is NEXT ");
//                getCurrentInputConnection().performEditorAction(this.currentEmojiViewPage);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//    public void onBackSpaceClicked(View view) {
//        try {
//            getCurrentInputConnection().sendKeyEvent(new KeyEvent(0, 67));
//            rFuvgVZhWUgsIPD();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//    public boolean onBackspaceTouch(View view, MotionEvent motionEvent) {
//        com.arvin.applekeyboard.tHMjXrnGstZwUss.EmojiViewPageModel jOgcKJVTGOIrhLr;
//        try {
//            if (motionEvent.getAction() == 0) {
//                com.arvin.applekeyboard.tHMjXrnGstZwUss.EmojiViewPageModel jOgcKJVTGOIrhLr2 = this.f3715currentEmojiViewPage;
//                if (jOgcKJVTGOIrhLr2 != null) {
//                    jOgcKJVTGOIrhLr2.isCaps(true);
//                    this.f3715currentEmojiViewPage.cancel(true);
//                }
//                com.arvin.applekeyboard.tHMjXrnGstZwUss.EmojiViewPageModel jOgcKJVTGOIrhLr3 = new com.arvin.applekeyboard.tHMjXrnGstZwUss.EmojiViewPageModel(new tKYPUSPhLJoTsAs(this));
//                this.f3715currentEmojiViewPage = jOgcKJVTGOIrhLr3;
//                jOgcKJVTGOIrhLr3.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
//            } else if (motionEvent.getAction() == 1 && (jOgcKJVTGOIrhLr = this.f3715currentEmojiViewPage) != null) {
//                jOgcKJVTGOIrhLr.isCaps(true);
//            }
//        } catch (Exception unused) {
//            Log.e("AppleKeyboardIME", "Error while back touch");
//        }
//        return false;
//    }
//
//    public View onCreateInputView() {
//        Log.i("AppleKeyboardIME", "onCreateInputView");
//        DkEqaUxxlOsNuQD();
//        this.cLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.key_preview, (ViewGroup) null);
//        KeyboardType keyboardType = this.keyboardType;
//        if (keyboardType == KeyboardType.QWERT) {
//            MyKeyboardView myKeyboardView = (MyKeyboardView) getLayoutInflater().inflate(R.layout.qwert_layout, (ViewGroup) null);
//            this.kView = myKeyboardView;
//            ButterKnife.qKxLAWwCNLplDeL(this, myKeyboardView);
//            this.keyCaps.setOnClickListener(this.f3716currentEmojiViewPage);
//            setKeyboardViewState();
//        } else if (keyboardType == KeyboardType.NUMBER_SYMBOL) {
//            MyKeyboardView myKeyboardView2 = (MyKeyboardView) getLayoutInflater().inflate(R.layout.numbers_symbuls_layout, (ViewGroup) null);
//            this.kView = myKeyboardView2;
//            ButterKnife.qKxLAWwCNLplDeL(this, myKeyboardView2);
//        } else if (keyboardType == KeyboardType.SYMBOL) {
//            MyKeyboardView myKeyboardView3 = (MyKeyboardView) getLayoutInflater().inflate(R.layout.symbuls_layout, (ViewGroup) null);
//            this.kView = myKeyboardView3;
//            ButterKnife.qKxLAWwCNLplDeL(this, myKeyboardView3);
//        } else if (keyboardType == KeyboardType.EMOJI) {
//            MyKeyboardView myKeyboardView4 = (MyKeyboardView) getLayoutInflater().inflate(R.layout.emoji_layout, (ViewGroup) null);
//            this.kView = myKeyboardView4;
//            ButterKnife.qKxLAWwCNLplDeL(this, myKeyboardView4);
//            ArrayList arrayList = new ArrayList();
//            arrayList.add(new EmojiPage(0));
//            arrayList.add(new EmojiPage(1));
//            arrayList.add(new EmojiPage(2));
//            arrayList.add(new EmojiPage(3));
//            arrayList.add(new EmojiPage(4));
//            arrayList.add(new EmojiPage(5));
//            MyEmojiPagerAdapter myEmojiPagerAdapter = new MyEmojiPagerAdapter(this.kView.getContext(), arrayList);
//            this.viewPager.setAdapter(myEmojiPagerAdapter);
//            this.viewPager.qKxLAWwCNLplDeL(new EmojiViewPageModel());
//            myEmojiPagerAdapter.setKeyboardViewState(new CixuzTvTCLmDHIY(this));
//            GYHOWQSyDKGpNuK.cUdJPWMmEiEWIka.cUdJPWMmEiEWIka.cUdJPWMmEiEWIka.zvdeMZJpeYKaEux.EmojiViewPageModel.jAUIhSqsKFgrqTN(this.kView.getContext());
//            ArrayList<GYHOWQSyDKGpNuK.cUdJPWMmEiEWIka.cUdJPWMmEiEWIka.cUdJPWMmEiEWIka.PMXTRzBEixJZbEu.EmojiViewPageModel> XfaXQGfGvYYcmfw2 = GYHOWQSyDKGpNuK.cUdJPWMmEiEWIka.cUdJPWMmEiEWIka.cUdJPWMmEiEWIka.zvdeMZJpeYKaEux.EmojiViewPageModel.isCaps().XfaXQGfGvYYcmfw();
//            if (XfaXQGfGvYYcmfw2 == null || XfaXQGfGvYYcmfw2.size() <= 10) {
//                this.viewPager.setCurrentItem(1);
//            } else {
//                setEmojiSelectionIcons();
//                this.ivRecent.setImageResource(R.mipmap.ic_recent_selected);
//                this.viewPager.setCurrentItem(0);
//            }
//        } else if (keyboardType == KeyboardType.PHONE) {
//            MyKeyboardView myKeyboardView5 = (MyKeyboardView) getLayoutInflater().inflate(R.layout.phone_number_layout, (ViewGroup) null);
//            this.kView = myKeyboardView5;
//            ButterKnife.qKxLAWwCNLplDeL(this, myKeyboardView5);
//        } else if (keyboardType == KeyboardType.NUMBER) {
//            MyKeyboardView myKeyboardView6 = (MyKeyboardView) getLayoutInflater().inflate(R.layout.number_layout, (ViewGroup) null);
//            this.kView = myKeyboardView6;
//            ButterKnife.qKxLAWwCNLplDeL(this, myKeyboardView6);
//        } else if (keyboardType == KeyboardType.PHONE_SYMBOLS) {
//            MyKeyboardView myKeyboardView7 = (MyKeyboardView) getLayoutInflater().inflate(R.layout.phone_number_symbols_layout, (ViewGroup) null);
//            this.kView = myKeyboardView7;
//            ButterKnife.qKxLAWwCNLplDeL(this, myKeyboardView7);
//        }
//        rFuvgVZhWUgsIPD();
//        setReturnText();
//        return this.kView;
//    }
//
//
//
//    public void onEmojiAnimalsClicked(View view) {
//        this.viewPager.setCurrentItem(2);
//    }
//
//
//
//    public void onEmojiFoodClicked(View view) {
//        this.viewPager.setCurrentItem(3);
//    }
//
//
//
//    public void onEmojiKeyboardClicked(View view) {
//        this.keyboardType = KeyboardType.EMOJI;
//        setInputView(onCreateInputView());
//    }
//
//
//
//    public void onEmojiRecentClicked(View view) {
//        this.viewPager.setCurrentItem(0);
//    }
//
//
//
//    public void onEmojiSmilesClicked(View view) {
//        this.viewPager.setCurrentItem(1);
//    }
//
//
//
//    public void onEmojiSymbolClicked(View view) {
//        this.viewPager.setCurrentItem(5);
//    }
//
//
//
//    public void onEmojiTravelClicked(View view) {
//        this.viewPager.setCurrentItem(4);
//    }
//
//
//
//    public void onKeyPressed(TextView textView) {
//        setTextOnInput(textView.getText().toString());
//    }
//
//
//
//    public void onNumClicked(View view) {
//        this.keyboardType = KeyboardType.NUMBER_SYMBOL;
//        setInputView(onCreateInputView());
//    }
//
//
//    public void onNumKeypadPressed(View view) {
//        String str = (String) view.getTag();
//        if (str.equals("phone_symbols")) {
//            this.keyboardType = KeyboardType.PHONE_SYMBOLS;
//            setInputView(onCreateInputView());
//        } else if (str.equals("phone_numbers")) {
//            this.keyboardType = KeyboardType.PHONE;
//            setInputView(onCreateInputView());
//        } else {
//            setTextOnInput(str);
//        }
//    }
//
//
//
//    public void onQWERTKeyboardClicked(View view) {
//        this.keyboardType = KeyboardType.QWERT;
//        setInputView(onCreateInputView());
//    }
//
//
//
//    public void onSettingsClicked(View view) {
//        Intent intent = new Intent(this, NewSettingsActivity.class);
//        intent.setFlags(268435456);
//        startActivity(intent);
//    }
//
//    public void onSpaceClicked(View view) {
//        setTextOnInput(" ");
//    }
//
//    public void onStartInput(EditorInfo editorInfo, boolean z) {
//        super.onStartInput(editorInfo, z);
//        touIgxGEecUheEv(getApplicationContext());
//        Log.i("AppleKeyboardIME", "onStartInput");
//        this.editorInfo = editorInfo;
//        this.currentEmojiViewPage = editorInfo.imeOptions;
//        this.qKxLAWwCNLplDeL = editorInfo.inputType;
//        Log.i("AppleKeyboardIME", "INPUT TYPE: " + this.qKxLAWwCNLplDeL);
//        int i = editorInfo.inputType & 15;
//        if (i == 1) {
//            this.keyboardType = KeyboardType.QWERT;
//        } else if (i == 2) {
//            this.keyboardType = KeyboardType.NUMBER;
//        } else if (i == 3) {
//            this.keyboardType = KeyboardType.PHONE;
//        } else if (i != 4) {
//            this.keyboardType = KeyboardType.QWERT;
//        } else {
//            this.keyboardType = KeyboardType.NUMBER_SYMBOL;
//        }
//        DkEqaUxxlOsNuQD();
//        setInputView(onCreateInputView());
//    }
//
//    public void onStartInputView(EditorInfo editorInfo, boolean z) {
//        super.onStartInputView(editorInfo, z);
//        Log.i("AppleKeyboardIME", "onStartInputView");
//    }
//    public void onSuggestionClicked(View view) {
//        String str;
//        switch (view.getId()) {
//            case R.id.layoutSug1 /*2131231114*/:
//                str = this.tvSug1.getText().toString();
//                break;
//            case R.id.layoutSug2 /*2131231115*/:
//                str = this.tvSug2.getText().toString();
//                break;
//            case R.id.layoutSug3 /*2131231116*/:
//                str = this.tvSug3.getText().toString();
//                break;
//            default:
//                str = BuildConfig.FLAVOR;
//                break;
//        }
//        if (!TextUtils.isEmpty(str)) {
//            onTextHandle(str);
//        }
//    }
//
//    public void onSymbolKeyboardClicked(View view) {
//        this.keyboardType = KeyboardType.SYMBOL;
//        setInputView(onCreateInputView());
//    }
//
//
//    public boolean onTouch(TextView textView, MotionEvent motionEvent) {
//        if (!this.rFuvgVZhWUgsIPD) {
//            return false;
//        }
//        MyKeyboardView myKeyboardView = this.kView;
//        if (motionEvent.getAction() == 0) {
//            try {
//                myKeyboardView.addView(this.cLayout, -1000, new ViewGroup.LayoutParams(-1, -1));
//            } catch (Exception e) {
//                Log.e("AppleKeyboardIME", "Unable to add key preview: " + e.getMessage());
//            }
//            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.cLayout.getLayoutParams();
//            layoutParams.height = textView.getMeasuredHeight() * 2;
//            layoutParams.width = textView.getMeasuredWidth() * 2;
//            layoutParams.leftMargin = ioDvLIaGvJsLPDs(textView) - (textView.getMeasuredWidth() / 2);
//            layoutParams.topMargin = (pSaDHeqYFoTHViy(textView) - textView.getMeasuredHeight()) + 4;
//            this.cLayout.setLayoutParams(layoutParams);
//            ((TextView) this.cLayout.findViewById(R.id.tvPreview)).setText(textView.getText().toString());
//            this.cLayout.setVisibility(0);
//        } else if (motionEvent.getAction() == 1) {
//            myKeyboardView.removeView(this.cLayout);
//        }
//        return false;
//    }
//}
//
