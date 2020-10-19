//package gautam.easydevelope.adapter;
//
//import android.content.Context;
//import android.support.annotation.LayoutRes;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import java.util.List;
//
//import cubewirs.booqzy.R;
//import cubewirs.booqzy.manager.OnRecyclerListItemClickListener;
//import cubewirs.booqzy.model.BuyBookModel;
//import gautam.easydevelope.R;
//
//public class MeRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    @LayoutRes
//    Integer resId = R.layout.inflator_buy_book_list;
//
//    private List<T> ts;
//    private Context context;
//    private final String TAG = getClass().getSimpleName();
//    private OnRecyclerListItemClickListener onRecyclerListItemClickListener = null;
//
//    public OnRecyclerListItemClickListener getOnRecyclerListItemClickListener() {
//        return onRecyclerListItemClickListener;
//    }
//
//    public void setOnRecyclerListItemClickListener(OnRecyclerListItemClickListener onRecyclerListItemClickListener) {
//        this.onRecyclerListItemClickListener = onRecyclerListItemClickListener;
//    }
//
//    public MeRecyclerViewAdapter(Context context, List<T> ts) {
//        this.ts = ts;
//        this.context = context;
//    }
//
//    @Override
//    public int getItemCount() {
//        return ts.size();
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(resId, parent, false);
//       return new MyViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        MyViewHolder viewHolder = (MyViewHolder) holder;
//        viewHolder.bindWith(position);
//    }
//
//
//    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//        private int position;
//
//        // declare view in holder
//        private TextView inflatorBookListTvTitle;
//
//        public MyViewHolder(View view) {
//            super(view);
//            // init view here
//            inflatorBookListTvTitle = (TextView) view.findViewById(R.id.inflator_book_list_tv_title);
//
//            inflatorBookListTvTitle.setOnClickListener(this);
//        }
//
//        private void bindWith(int position) {
//            this.position = position;
//            // set views data here
//            if (ts.get(position) instanceof BuyBookModel){
//             BuyBookModel model = (BuyBookModel) ts.get(position);
//                inflatorBookListTvTitle.setText(model.getBookName());
//            }
//        }
//
//        @Override
//        public void onClick(View v) {
//            if (onRecyclerListItemClickListener != null)
//                onRecyclerListItemClickListener.onItemClick(v, ts.get(position), position);
//        }
//    }
//
//}
