
package com.mp.android.apps.monke.monkeybook.presenter;

import android.app.Activity;

import com.mp.android.apps.monke.basemvplib.IPresenter;
import com.mp.android.apps.monke.monkeybook.bean.BookShelfBean;
import com.mp.android.apps.monke.monkeybook.presenter.impl.ReadBookPresenterImpl;
import com.mp.android.apps.monke.monkeybook.widget.contentswitchview.BookContentView;

public interface IBookReadPresenter extends IPresenter {

    int getOpen_from();

    BookShelfBean getBookShelf();

    /**
     * 初始化阅读页数据
     */
    void initContent();

    void loadContent(BookContentView bookContentView, long bookTag, final int chapterIndex, final int page);

    void updateProgress(int chapterIndex, int pageIndex);

    /**
     * 保存读书进度
     */
    void saveProgress();

    String getChapterTitle(int chapterIndex);

    void setPageLineCount(int pageLineCount);

    void addToShelf(final ReadBookPresenterImpl.OnAddListner addListner);

    Boolean getAdd();

    void initData(Activity activity);

    void openBookFromOther(Activity activity);
}
