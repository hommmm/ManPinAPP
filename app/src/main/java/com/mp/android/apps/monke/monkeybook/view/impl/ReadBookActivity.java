
package com.mp.android.apps.monke.monkeybook.view.impl;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.NonNull;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hwangjr.rxbus.RxBus;
import com.mp.android.apps.monke.basemvplib.AppActivityManager;
import com.mp.android.apps.R;
import com.mp.android.apps.monke.monkeybook.ReadBookControl;
import com.mp.android.apps.monke.monkeybook.base.MBaseActivity;
import com.mp.android.apps.monke.monkeybook.bean.DownloadChapterBean;
import com.mp.android.apps.monke.monkeybook.bean.DownloadChapterListBean;
import com.mp.android.apps.monke.monkeybook.common.RxBusTag;
import com.mp.android.apps.monke.monkeybook.presenter.IBookReadPresenter;
import com.mp.android.apps.monke.monkeybook.presenter.impl.ReadBookPresenterImpl;
import com.mp.android.apps.monke.monkeybook.utils.DensityUtil;
import com.mp.android.apps.monke.monkeybook.utils.PremissionCheck;
import com.mp.android.apps.monke.monkeybook.view.IBookReadView;
import com.mp.android.apps.monke.monkeybook.view.popupwindow.CheckAddShelfPop;

import com.mp.android.apps.monke.monkeybook.view.popupwindow.MoreSettingPop;
import com.mp.android.apps.monke.monkeybook.view.popupwindow.ReadBookMenuMorePop;
import com.mp.android.apps.monke.monkeybook.view.popupwindow.WindowLightPop;
import com.mp.android.apps.monke.monkeybook.widget.ChapterListView;
import com.mp.android.apps.monke.monkeybook.widget.contentswitchview.BookContentView;
import com.mp.android.apps.monke.monkeybook.widget.contentswitchview.ContentSwitchView;
import com.mp.android.apps.monke.monkeybook.widget.modialog.MoProgressHUD;
import com.monke.mprogressbar.MHorProgressBar;
import com.monke.mprogressbar.OnProgressListener;

import java.util.ArrayList;
import java.util.List;

import me.grantland.widget.AutofitTextView;

public class ReadBookActivity extends MBaseActivity<IBookReadPresenter> implements IBookReadView, View.OnClickListener {

    private FrameLayout flContent;

    private ContentSwitchView csvBook;

    //主菜单
    private FrameLayout flMenu;
    private View vMenuBg;
    private LinearLayout llMenuTop;
    private LinearLayout llMenuBottom;
    /**
     * 阅读页面返回按钮
     */
    private ImageButton ivReturn;
    /**
     * 右侧菜单按钮，弹出离线下载
     */
    private ImageView ivMenuMore;
    /**
     * 章节提示
     */
    private AutofitTextView atvTitle;
    private TextView tvPre;
    private TextView tvNext;
    private MHorProgressBar hpbReadProgress;
    private LinearLayout llCatalog;
    private LinearLayout llLight;
    private LinearLayout llNightMode;
    /**
     * 设置按钮，弹出 字号 背景等设置内容
     */
    private LinearLayout llSetting;
    //主菜单动画
    private Animation menuTopIn;
    private Animation menuTopOut;
    private Animation menuBottomIn;
    private Animation menuBottomOut;

    private CheckAddShelfPop checkAddShelfPop;
    private ChapterListView chapterListView;
    private WindowLightPop windowLightPop;
    /**
     * 离线下载popWindow
     */
    private ReadBookMenuMorePop readBookMenuMorePop;

    private MoreSettingPop moreSettingPop;

    private MoProgressHUD moProgressHUD;

    private TextView ll_scene_text;

    private final String dayMessage = "白天";
    private final String nightMessage = "夜间";

    @Override
    protected IBookReadPresenter initInjector() {
        return new ReadBookPresenterImpl();
    }

    @Override
    protected void onCreateActivity() {
        setContentView(R.layout.activity_bookread);
    }

    @Override
    protected void initData() {
        mPresenter.saveProgress();

        //初始化头部及尾部
        menuTopIn = AnimationUtils.loadAnimation(this, R.anim.anim_readbook_top_in);
        menuBottomIn = AnimationUtils.loadAnimation(this, R.anim.anim_readbook_bottom_in);
        menuTopOut = AnimationUtils.loadAnimation(this, R.anim.anim_readbook_top_out);
        menuBottomOut = AnimationUtils.loadAnimation(this, R.anim.anim_readbook_bottom_out);

        menuTopIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                vMenuBg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        llMenuTop.startAnimation(menuTopOut);
                        llMenuBottom.startAnimation(menuBottomOut);
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        menuTopOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                vMenuBg.setOnClickListener(null);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                flMenu.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void bindView() {
        moProgressHUD = new MoProgressHUD(this);
        flContent = (FrameLayout) findViewById(R.id.fl_content);
        csvBook = (ContentSwitchView) findViewById(R.id.csv_book);
        flMenu = (FrameLayout) findViewById(R.id.fl_menu);
        vMenuBg = findViewById(R.id.v_menu_bg);
        llMenuTop = (LinearLayout) findViewById(R.id.ll_menu_top);
        llMenuBottom = (LinearLayout) findViewById(R.id.ll_menu_bottom);
        ivReturn = (ImageButton) findViewById(R.id.iv_return);
        ivMenuMore = (ImageView) findViewById(R.id.iv_more);
        atvTitle = (AutofitTextView) findViewById(R.id.atv_title);
        tvPre = (TextView) findViewById(R.id.tv_pre);
        tvNext = (TextView) findViewById(R.id.tv_next);
        hpbReadProgress = (MHorProgressBar) findViewById(R.id.hpb_read_progress);
        llCatalog = (LinearLayout) findViewById(R.id.ll_catalog);
        llLight = (LinearLayout) findViewById(R.id.ll_light);
        llNightMode = (LinearLayout) findViewById(R.id.ll_night_mode);
        ll_scene_text = findViewById(R.id.ll_scene_text);
        llSetting = (LinearLayout) findViewById(R.id.ll_setting);
        chapterListView = (ChapterListView) findViewById(R.id.clp_chapterlist);
    }

    @Override
    protected void firstRequest() {
        super.firstRequest();
        initCsvBook();
        if (ReadBookControl.getInstance().getTextDrawableIndex() == 3) {
            ll_scene_text.setText(dayMessage);
        } else {
            ll_scene_text.setText(nightMessage);
        }
    }

    @Override
    public void setHpbReadProgressMax(int count) {
        hpbReadProgress.setMaxProgress(count);
    }

    private void initCsvBook() {
        csvBook.bookReadInit(new ContentSwitchView.OnBookReadInitListener() {
            @Override
            public void success() {
                mPresenter.initData(ReadBookActivity.this);
            }
        });
    }

    @Override
    public void initPop() {
        checkAddShelfPop = new CheckAddShelfPop(this, mPresenter.getBookShelf().getBookInfoBean().getName(), new CheckAddShelfPop.OnItemClickListener() {
            @Override
            public void clickExit() {
                finish();
            }

            @Override
            public void clickAddShelf() {
                mPresenter.addToShelf(null);
                checkAddShelfPop.dismiss();
            }
        });
        chapterListView.setData(mPresenter.getBookShelf(), new ChapterListView.OnItemClickListener() {
            @Override
            public void itemClick(int index) {
                csvBook.setInitData(index, mPresenter.getBookShelf().getBookInfoBean().getChapterlist().size(), BookContentView.DURPAGEINDEXBEGIN);
            }
        });

        windowLightPop = new WindowLightPop(this);
        windowLightPop.initLight();

        readBookMenuMorePop = new ReadBookMenuMorePop(this);
        readBookMenuMorePop.setOnClickDownload(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readBookMenuMorePop.dismiss();
                if (flMenu.getVisibility() == View.VISIBLE) {
                    llMenuTop.startAnimation(menuTopOut);
                    llMenuBottom.startAnimation(menuBottomOut);
                }
                //弹出离线下载界面
                int endIndex = mPresenter.getBookShelf().getDurChapter() + 50;
                if (endIndex >= mPresenter.getBookShelf().getBookInfoBean().getChapterlist().size()) {
                    endIndex = mPresenter.getBookShelf().getBookInfoBean().getChapterlist().size() - 1;
                }
                moProgressHUD.showDownloadList(mPresenter.getBookShelf().getDurChapter(), endIndex, mPresenter.getBookShelf().getBookInfoBean().getChapterlist().size(), new MoProgressHUD.OnClickDownload() {
                    @Override
                    public void download(final int start, final int end) {
                        moProgressHUD.dismiss();
                        mPresenter.addToShelf(new ReadBookPresenterImpl.OnAddListner() {
                            @Override
                            public void addSuccess() {
                                List<DownloadChapterBean> result = new ArrayList<DownloadChapterBean>();
                                for (int i = start; i <= end; i++) {
                                    DownloadChapterBean item = new DownloadChapterBean();
                                    item.setNoteUrl(mPresenter.getBookShelf().getNoteUrl());
                                    item.setDurChapterIndex(mPresenter.getBookShelf().getBookInfoBean().getChapterlist().get(i).getDurChapterIndex());
                                    item.setDurChapterName(mPresenter.getBookShelf().getBookInfoBean().getChapterlist().get(i).getDurChapterName());
                                    item.setDurChapterUrl(mPresenter.getBookShelf().getBookInfoBean().getChapterlist().get(i).getDurChapterUrl());
                                    item.setTag(mPresenter.getBookShelf().getTag());
                                    item.setBookName(mPresenter.getBookShelf().getBookInfoBean().getName());
                                    item.setCoverUrl(mPresenter.getBookShelf().getBookInfoBean().getCoverUrl());
                                    result.add(item);
                                }
                                RxBus.get().post(RxBusTag.ADD_DOWNLOAD_TASK, new DownloadChapterListBean(result));
                            }
                        });

                    }
                });
            }
        });

        moreSettingPop = new MoreSettingPop(this, new MoreSettingPop.OnChangeProListener() {
            @Override
            public void textChange(int index) {
                csvBook.changeTextSize();
            }

            @Override
            public void bgChange(int index) {
                csvBook.changeBg();
            }

            @Override
            public void isDay(boolean isDay) {
                if (isDay) {
                    ll_scene_text.setText(nightMessage);
                } else {
                    ll_scene_text.setText(dayMessage);
                }

            }


        });
    }

    @Override
    protected void bindEvent() {
        hpbReadProgress.setProgressListener(new OnProgressListener() {
            @Override
            public void moveStartProgress(float dur) {

            }

            @Override
            public void durProgressChange(float dur) {

            }

            @Override
            public void moveStopProgress(float dur) {
                int realDur = (int) Math.ceil(dur);
                if (realDur < 1) {
                    realDur = 1;
                }
                if ((realDur - 1) != mPresenter.getBookShelf().getDurChapter()) {
                    csvBook.setInitData(realDur - 1, mPresenter.getBookShelf().getBookInfoBean().getChapterlist().size(), BookContentView.DURPAGEINDEXBEGIN);
                }
                if (hpbReadProgress.getDurProgress() != realDur)
                    hpbReadProgress.setDurProgress(realDur);
            }

            @Override
            public void setDurProgress(float dur) {
                if (hpbReadProgress.getMaxProgress() == 1) {
                    tvPre.setEnabled(false);
                    tvNext.setEnabled(false);
                } else {
                    if (dur == 1) {
                        tvPre.setEnabled(false);
                        tvNext.setEnabled(true);
                    } else if (dur == hpbReadProgress.getMaxProgress()) {
                        tvPre.setEnabled(true);
                        tvNext.setEnabled(false);
                    } else {
                        tvPre.setEnabled(true);
                        tvNext.setEnabled(true);
                    }
                }
            }
        });
        csvBook.setLoadDataListener(new ContentSwitchView.LoadDataListener() {
            @Override
            public void loaddata(BookContentView bookContentView, long qtag, int chapterIndex, int pageIndex) {
                mPresenter.loadContent(bookContentView, qtag, chapterIndex, pageIndex);
            }

            @Override
            public void updateProgress(int chapterIndex, int pageIndex) {
                mPresenter.updateProgress(chapterIndex, pageIndex);

                if (mPresenter.getBookShelf().getBookInfoBean().getChapterlist().size() > 0)
                    atvTitle.setText(mPresenter.getBookShelf().getBookInfoBean().getChapterlist().get(mPresenter.getBookShelf().getDurChapter()).getDurChapterName());
                else
                    atvTitle.setText("无章节");
                if (hpbReadProgress.getDurProgress() != chapterIndex + 1)
                    hpbReadProgress.setDurProgress(chapterIndex + 1);
            }

            @Override
            public String getChapterTitle(int chapterIndex) {
                return mPresenter.getChapterTitle(chapterIndex);
            }

            @Override
            public void initData(int lineCount) {
                mPresenter.setPageLineCount(lineCount);
                mPresenter.initContent();
            }

            @Override
            public void showMenu() {
                flMenu.setVisibility(View.VISIBLE);
                llMenuTop.startAnimation(menuTopIn);
                llMenuBottom.startAnimation(menuBottomIn);
            }
        });


        ivReturn.setOnClickListener(this);
        ivMenuMore.setOnClickListener(this);
        tvPre.setOnClickListener(this);
        tvNext.setOnClickListener(this);
        llCatalog.setOnClickListener(this);
        llLight.setOnClickListener(this);
        llNightMode.setOnClickListener(this);
        llSetting.setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ll_setting:
                llMenuTop.startAnimation(menuTopOut);
                llMenuBottom.startAnimation(menuBottomOut);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        moreSettingPop.showAtLocation(flContent, Gravity.BOTTOM, 0, 0);
                    }
                }, menuTopOut.getDuration());
                break;
            case R.id.ll_night_mode:
                if (ll_scene_text.getText().equals(dayMessage)) {
                    ll_scene_text.setText(nightMessage);
                    ReadBookControl.getInstance().setTextDrawableIndex(ReadBookControl.getInstance().getDayColorIndex());
                    moreSettingPop.updateBg(ReadBookControl.getInstance().getDayColorIndex());
                    csvBook.changeBg();
                } else {
                    ll_scene_text.setText(dayMessage);
                    ReadBookControl.getInstance().setTextDrawableIndex(3);
                    moreSettingPop.updateBg(3);
                    csvBook.changeBg();
                }
                break;
            case R.id.ll_light:
                llMenuTop.startAnimation(menuTopOut);
                llMenuBottom.startAnimation(menuBottomOut);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        windowLightPop.showAtLocation(flContent, Gravity.BOTTOM, 0, 0);
                    }
                }, menuTopOut.getDuration());
                break;
            case R.id.ll_catalog:
                llMenuTop.startAnimation(menuTopOut);
                llMenuBottom.startAnimation(menuBottomOut);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        chapterListView.show(mPresenter.getBookShelf().getDurChapter());
                    }
                }, menuTopOut.getDuration());
                break;
            case R.id.tv_next:
                csvBook.setInitData(mPresenter.getBookShelf().getDurChapter() + 1, mPresenter.getBookShelf().getBookInfoBean().getChapterlist().size(), BookContentView.DURPAGEINDEXBEGIN);
                break;
            case R.id.tv_pre:
                csvBook.setInitData(mPresenter.getBookShelf().getDurChapter() - 1, mPresenter.getBookShelf().getBookInfoBean().getChapterlist().size(), BookContentView.DURPAGEINDEXBEGIN);
                break;
            case R.id.iv_more:
                readBookMenuMorePop.showAsDropDown(ivMenuMore, 0, DensityUtil.dp2px(ReadBookActivity.this, -3.5f));
                break;
            case R.id.iv_return:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public Paint getPaint() {
        return csvBook.getTextPaint();
    }

    @Override
    public int getContentWidth() {
        return csvBook.getContentWidth();
    }

    @Override
    public void initContentSuccess(int durChapterIndex, int chapterAll, int durPageIndex) {
        csvBook.setInitData(durChapterIndex, chapterAll, durPageIndex);
    }

    @Override
    public void startLoadingBook() {
        csvBook.startLoading();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.saveProgress();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Boolean mo = moProgressHUD.onKeyDown(keyCode, event);
        if (mo)
            return mo;
        else {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (flMenu.getVisibility() == View.VISIBLE) {
                    llMenuTop.startAnimation(menuTopOut);
                    llMenuBottom.startAnimation(menuBottomOut);
                    return true;
                } else if (!mPresenter.getAdd() && checkAddShelfPop != null && !checkAddShelfPop.isShowing()) {
                    checkAddShelfPop.showAtLocation(flContent, Gravity.CENTER, 0, 0);
                    return true;
                } else {
                    Boolean temp2 = chapterListView.dimissChapterList();
                    if (temp2)
                        return true;
                    else {
                        finish();
                        return true;
                    }
                }
            } else {
                Boolean temp = csvBook.onKeyDown(keyCode, event);
                if (temp)
                    return true;
            }
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Boolean temp = csvBook.onKeyUp(keyCode, event);
        if (temp)
            return true;
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void showLoadBook() {
        moProgressHUD.showLoading("文本导入中...");
    }

    @Override
    public void dimissLoadBook() {
        moProgressHUD.dismiss();
    }

    @Override
    public void loadLocationBookError() {
        csvBook.loadError();
    }

    @Override
    public void showDownloadMenu() {
        ivMenuMore.setVisibility(View.VISIBLE);
    }

    private Boolean showCheckPremission = false;


    @Override
    protected void onResume() {
        super.onResume();
        if (showCheckPremission && mPresenter.getOpen_from() == ReadBookPresenterImpl.OPEN_FROM_OTHER && !(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !PremissionCheck.checkPremission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE))) {
            showCheckPremission = true;
            mPresenter.openBookFromOther(this);
        }
    }

    @Override
    public void finish() {
        if (!AppActivityManager.getInstance().isExist(BookMainActivity.class)) {
            Intent intent = new Intent(this, BookMainActivity.class);
            startActivity(intent);
        }
        super.finish();
    }


}