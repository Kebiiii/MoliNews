package com.liukebing.molinews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.liukebing.molinews.R;
import com.liukebing.molinews.entity.BannerBean;
import com.liukebing.molinews.entity.Data;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.List;

/**
 * 首页数据适配器
 * Created by HLQ on 2016/09/01.
 */
public class FirstPageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //首个位置显示轮播图片
    private static final int FIRST = 0;
    //默认位置显示图文
    private static final int NORMAL = 1;
    //最后位置显示加载更多
    private static final int LAST = 2;

    private Context context;

    //存储数据的集合
    private List<Data> list;

    private BannerBean bannerBean;

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view,int position);
    }

    public FirstPageAdapter(Context context,List<Data> list,BannerBean bannerBean){

        this.context = context;
        this.list = list;
        this.bannerBean = bannerBean;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;

        //根据viewType判断要创建的布局
        if(viewType==FIRST){
            viewHolder = new BannerViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_banner,parent,false));
        }else if(viewType==LAST){
            viewHolder = new FooterViewHolder(LayoutInflater.from(context).inflate(R.layout.item_footer,parent,false));
        }else{
            viewHolder = new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_item,parent,false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof BannerViewHolder){
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            //由于加载图片比较慢，所以setImages应该放在setBannerTitle后面，不然会报空指针
            bannerViewHolder.banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
            bannerViewHolder.banner.setBannerTitle(bannerBean.getTitle());
            bannerViewHolder.banner.setImages(bannerBean.getThumbnail());
        } else if(holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            Data data = list.get(position-1);
            itemViewHolder.draweeView.setImageURI(data.getThumbnail());
            itemViewHolder.tv_news.setText(data.getTitle());
        } else if(holder instanceof FooterViewHolder) {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;

            if(position==97){
                footerViewHolder.pb_footer.setVisibility(View.GONE);
                footerViewHolder.tv_footer.setGravity(Gravity.CENTER_HORIZONTAL);
                footerViewHolder.tv_footer.setText("已没有更多数据");
            }
        }

        if(listener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                //getLayoutPosition防止item位置乱序
                @Override
                public void onClick(View view) {
                    listener.onItemClick(holder.itemView,holder.getLayoutPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onItemLongClick(holder.itemView,holder.getLayoutPosition());
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(list!=null&&list.size()>0) {

            return list.size()+2;
        }
        return 0;
    }

    //根据位置去填充不同的ViewHolder
    @Override
    public int getItemViewType(int position) {

        if(position==0){
            return FIRST;

            //如果当前位置加1为最后一个item
        }else if(position+1==getItemCount()){
            return LAST;
        }else{
            return NORMAL;
        }
    }

    /**
     * 图文的ViewHolder
     */
    static class ItemViewHolder extends RecyclerView.ViewHolder{

        SimpleDraweeView draweeView;
        TextView tv_news;

        //itemView表示对应的item布局
        public ItemViewHolder(View itemView) {
            super(itemView);
            draweeView = (SimpleDraweeView) itemView.findViewById(R.id.sdv_news);
            tv_news = (TextView) itemView.findViewById(R.id.tv_news);
        }
    }

    /**
     * Banner的ViewHolder
     */
    static class BannerViewHolder extends RecyclerView.ViewHolder{

        Banner banner;

        public BannerViewHolder(View itemView) {
            super(itemView);

            banner = (Banner) itemView.findViewById(R.id.banner);
        }
    }

    /**
     * Footer的ViewHolder
     */
    static class FooterViewHolder extends RecyclerView.ViewHolder{

        TextView tv_footer;
        ProgressBar pb_footer;

        public FooterViewHolder(View itemView) {
            super(itemView);

            tv_footer = (TextView) itemView.findViewById(R.id.tv_footer);
            pb_footer = (ProgressBar) itemView.findViewById(R.id.pb_footer);
        }
    }
}
