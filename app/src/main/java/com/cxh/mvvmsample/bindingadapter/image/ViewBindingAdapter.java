package com.cxh.mvvmsample.bindingadapter.image;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.ImageView;

import com.cxh.mvvmsample.bindingadapter.ReplyCommand;
import com.facebook.common.executors.UiThreadImmediateExecutorService;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.socks.library.KLog;


public final class ViewBindingAdapter {

    /**
     * setBackground接收的是一个Drawable对象，而我们传入的是个string，所以我们此处可以用@BindingConversion来转换一下
     */
    @BindingConversion
    public static Drawable colorToDrawable(String color) {
        return new ColorDrawable(Color.parseColor(color));
    }


    @BindingAdapter({"path"})
    public static void setImageUri(SimpleDraweeView simpleDraweeView, String uri) {
        KLog.d(uri);
        if (!TextUtils.isEmpty(uri)) {
            simpleDraweeView.setImageURI(Uri.parse(uri));
        }
    }

    /**
     * 把上面的path换成uri，此时这个会覆盖上面的方法，因为SimpleDraweeView是ImageView的子类，requireAll 参数是否全部必须
     */
    @BindingAdapter(value = {"uri", "placeholderImageRes", "request_width", "request_height", "onSuccessCommand", "onFailureCommand"}, requireAll = false)
    public static void loadImage(final ImageView imageView, String uri,
                                 int placeholderImageRes,
                                 int width, int height,
                                 final ReplyCommand<Bitmap> onSuccessCommand,
                                 final ReplyCommand<DataSource<CloseableReference<CloseableImage>>> onFailureCommand) {
        KLog.d("tag", uri, placeholderImageRes, width, height);
        imageView.setImageResource(placeholderImageRes);
        if (!TextUtils.isEmpty(uri)) {
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            ImageRequestBuilder builder = ImageRequestBuilder.newBuilderWithSource(Uri.parse(uri));
            // 调整大小，比如大图
            if (width > 0 && height > 0) {
                builder.setResizeOptions(new ResizeOptions(width, height));
            }
            ImageRequest request = builder.build();

            DataSource<CloseableReference<CloseableImage>> dataSource = imagePipeline.fetchDecodedImage(request, imageView.getContext());

            dataSource.subscribe(new BaseBitmapDataSubscriber() {
                @Override
                protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {
                    if (onFailureCommand != null) {
                        onFailureCommand.execute(dataSource);
                    }
                }

                @Override
                protected void onNewResultImpl(Bitmap bitmap) {
                    KLog.d("", bitmap.getWidth(), bitmap.getHeight());
                    imageView.setImageBitmap(bitmap);
                    if (onSuccessCommand != null) {
                        onSuccessCommand.execute(bitmap);
                    }
                }
            }, UiThreadImmediateExecutorService.getInstance());

        }
    }
}

