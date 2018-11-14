package com.kroraina.easyreader.model.remote;

import com.kroraina.easyreader.model.bean.BillBookBean;
import com.kroraina.easyreader.model.bean.BookCommentBean;
import com.kroraina.easyreader.model.bean.BookDetailRecommendListBean;
import com.kroraina.easyreader.model.bean.BookHelpsBean;
import com.kroraina.easyreader.model.bean.BookListBean;
import com.kroraina.easyreader.model.bean.BookReviewBean;
import com.kroraina.easyreader.model.bean.BookTagBean;
import com.kroraina.easyreader.model.bean.ChapterInfoBean;
import com.kroraina.easyreader.model.bean.CommentBean;
import com.kroraina.easyreader.model.bean.CommentDetailBean;
import com.kroraina.easyreader.model.bean.HotCommentBean;
import com.kroraina.easyreader.model.bean.ReviewDetailBean;
import com.kroraina.easyreader.model.bean.packages.BookSubSortPackage;
import com.kroraina.easyreader.model.bean.packages.HotWordPackage;
import com.kroraina.easyreader.model.bean.packages.KeyWordPackage;
import com.kroraina.easyreader.model.entity.BookChapterBean;
import com.kroraina.easyreader.model.entity.CollBookBean;
import com.kroraina.easyreader.modules.book.detail.BookDetailBean;
import com.kroraina.easyreader.modules.category.BookSortPackage;
import com.kroraina.easyreader.modules.category.detail.SortBookBean;
import com.kroraina.easyreader.modules.community.detail.HelpsDetailBean;
import com.kroraina.easyreader.modules.community.discuss.BookDiscussBean;
import com.kroraina.easyreader.modules.rank.BillboardPackage;
import com.kroraina.easyreader.modules.search.SearchBookPackage;
import com.kroraina.easyreader.modules.sheetlist.detail.BookListDetailBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;



public class RemoteRepository {
    private static RemoteRepository sInstance;
    private BookApi mBookApi;

    private RemoteRepository(){
        mBookApi = RemoteHelper.getInstance().getRetrofit().create(BookApi.class);
    }

    public static RemoteRepository getInstance(){
        if (sInstance == null){
            synchronized (RemoteHelper.class){
                if (sInstance == null){
                    sInstance = new RemoteRepository();
                }
            }
        }
        return sInstance;
    }

    public Single<List<CollBookBean>> getRecommendBooks(String gender){
        return mBookApi.getRecommendBookPackage(gender)
                .map(bean -> bean.getBooks());
    }

    public Single<List<BookChapterBean>> getBookChapters(String bookId){
        return mBookApi.getBookChapterPackage(bookId, "chapter")
                .map(bean -> {
                    if (bean.getMixToc() == null){
                        return new ArrayList<BookChapterBean>(1);
                    }
                    else {
                        return bean.getMixToc().getChapters();
                    }
                });
    }

    /**
     * 注意这里用的是同步请求
     * @param url
     * @return
     */
    public Single<ChapterInfoBean> getChapterInfo(String url){
        return mBookApi.getChapterInfoPackage(url)
                .map(bean -> bean.getChapter());
    }

    /***********************************************************************************/


    public Single<List<BookCommentBean>> getBookComment(String block, String sort, int start, int limit, String distillate){

        return mBookApi.getBookCommentList(block,"all",sort,"all",start+"",limit+"",distillate)
                .map((listBean)-> listBean.getPosts());
    }

    public Single<List<BookHelpsBean>> getBookHelps(String sort, int start, int limit, String distillate){
        return mBookApi.getBookHelpList("all",sort,start+"",limit+"",distillate)
                .map((listBean)-> listBean.getHelps());
    }

    public Single<List<BookReviewBean>> getBookReviews(String sort, String bookType, int start, int limited, String distillate){
        return mBookApi.getBookReviewList("all",sort,bookType,start+"",limited+"",distillate)
                .map(listBean-> listBean.getReviews());
    }

    public Single<CommentDetailBean> getCommentDetail(String detailId){
        return mBookApi.getCommentDetailPackage(detailId)
                .map(bean -> bean.getPost());
    }

    public Single<ReviewDetailBean> getReviewDetail(String detailId){
        return mBookApi.getReviewDetailPacakge(detailId)
                .map(bean -> bean.getReview());
    }

    public Single<HelpsDetailBean> getHelpsDetail(String detailId){
        return mBookApi.getHelpsDetailPackage(detailId)
                .map(bean -> bean.getHelp());
    }

    public Single<List<CommentBean>> getBestComments(String detailId){
        return mBookApi.getBestCommentPackage(detailId)
                .map(bean -> bean.getComments());
    }

    /**
     * 获取的是 综合讨论区的 评论
     * @param detailId
     * @param start
     * @param limit
     * @return
     */
    public Single<List<CommentBean>> getDetailComments(String detailId, int start, int limit){
        return mBookApi.getCommentPackage(detailId,start+"",limit+"")
                .map(bean -> bean.getComments());
    }

    /**
     * 获取的是 书评区和书荒区的 评论
     * @param detailId
     * @param start
     * @param limit
     * @return
     */
    public Single<List<CommentBean>> getDetailBookComments(String detailId, int start, int limit){
        return mBookApi.getBookCommentPackage(detailId,start+"",limit+"")
                .map(bean -> bean.getComments());
    }

    /*****************************************************************************/
    /**
     * 获取书籍的分类
     * @return
     */
    public Single<BookSortPackage> getBookSortPackage(){
        return mBookApi.getBookSortPackage();
    }

    /**
     * 获取书籍的子分类
     * @return
     */
    public Single<BookSubSortPackage> getBookSubSortPackage(){
        return mBookApi.getBookSubSortPackage();
    }

    /**
     * 根据分类获取书籍列表
     * @param gender
     * @param type
     * @param major
     * @param minor
     * @param start
     * @param limit
     * @return
     */
    public Single<List<SortBookBean>> getSortBooks(String gender,String type,String major,String minor,int start,int limit){
        return mBookApi.getSortBookPackage(gender, type, major, minor, start, limit)
                .map(bean -> bean.getBooks());
    }

    /*******************************************************************************/

    /**
     * 排行榜的类型
     * @return
     */
    public Single<BillboardPackage> getBillboardPackage(){
        return mBookApi.getBillboardPackage();
    }

    /**
     * 排行榜的书籍
     * @param billId
     * @return
     */
    public Single<List<BillBookBean>> getBillBooks(String billId){
        return mBookApi.getBillBookPackage(billId)
                .map(bean -> bean.getRanking().getBooks());
    }

    /***********************************书单*************************************/

    /**
     * 获取书单列表
     * @param duration
     * @param sort
     * @param start
     * @param limit
     * @param tag
     * @param gender
     * @return
     */
    public Single<List<BookListBean>> getBookLists(String duration, String sort,
                                                   int start, int limit,
                                                   String tag, String gender){
        return mBookApi.getBookListPackage(duration, sort, start+"", limit+"", tag, gender)
                .map(bean -> bean.getBookLists());
    }

    /**
     * 获取书单的标签|类型
     * @return
     */
    public Single<List<BookTagBean>> getBookTags(){
        return mBookApi.getBookTagPackage()
                .map(bean -> bean.getData());
    }

    /**
     * 获取书单的详情
     * @param detailId
     * @return
     */
    public Single<BookListDetailBean> getBookListDetail(String detailId){
        return mBookApi.getBookListDetailPackage(detailId)
                .map(bean -> bean.getBookList());
    }

    /***************************************书籍详情**********************************************/
    public Single<BookDetailBean> getBookDetail(String bookId){
        return mBookApi.getBookDetail(bookId);
    }

    public Single<List<HotCommentBean>> getHotComments(String bookId){
        return mBookApi.getHotCommnentPackage(bookId)
                .map(bean -> bean.getReviews());
    }

    public Single<List<BookDetailRecommendListBean>> getRecommendBookList(String bookId, int limit){
        return mBookApi.getRecommendBookListPackage(bookId,limit+"")
                .map(bean -> bean.getBooklists());
    }
    /********************************书籍搜索*********************************************/
    /**
     * 搜索热词
     * @return
     */
    public Single<List<String>> getHotWords(){
        return mBookApi.getHotWordPackage().map(HotWordPackage::getHotWords);
    }

    /**
     * 搜索关键字
     * @param query
     * @return
     */
    public Single<List<String>> getKeyWords(String query){
        return mBookApi.getKeyWordPacakge(query).map(KeyWordPackage::getKeywords);

    }

    /**
     * 查询书籍
     * @param query:书名|作者名
     * @return
     */
    public Single<List<SearchBookPackage.BooksBean>> getSearchBooks(String query){
        return mBookApi.getSearchBookPackage(query).map(SearchBookPackage::getBooks);
    }







    public Single<List<BookDiscussBean>> getBookDiscuss(String bookId, String sort, int start, int limit){

        return mBookApi.getBookDiscussList(bookId,sort,start+"",limit+"")
                .map((listBean)-> listBean.getPosts());
    }

    public Single<List<com.kroraina.easyreader.modules.community.comment.BookCommentBean>> getBookComment(String bookId, String sort, int start, int limit){

        return mBookApi.getBookCommentList(bookId,sort,start+"",limit+"")
                .map((listBean)-> listBean.getReviews());
    }

    public Single<List<com.kroraina.easyreader.modules.community.comment.BookCommentBean>> getBookShortComment(String bookId, String sort, int start, int limit){

        return mBookApi.getBookShortCommentList(bookId,sort,start+"",limit+"")
                .map((listBean)-> listBean.getReviews());
    }
}
