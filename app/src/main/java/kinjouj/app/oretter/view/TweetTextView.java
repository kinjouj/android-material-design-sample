package kinjouj.app.oretter.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Layout;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.MotionEvent;
import android.webkit.URLUtil;
import android.widget.TextView;
import android.widget.Toast;
import com.twitter.Regex;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.fragments.list.status.UserFragment;
import kinjouj.app.oretter.view.manager.TabLayoutManager;

public class TweetTextView extends TextView {

    private static final String UNKNOWN_PATTERN_STRING = "unknown pattern: \"%s\"";
    private static final String FULLWIDTH_NUMBER_SIGN = "\uFF03";
    private static final String FULLWIDTH_COMMERCIAL_AT = "\uFF20";

    public TweetTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void linkify(String text) {
        setText(text);
        Linkify.addLinks(this, Regex.VALID_MENTION_OR_LIST, null);
        Linkify.addLinks(this, Regex.VALID_HASHTAG, null);
        Linkify.addLinks(this, Patterns.WEB_URL, null);
        setMovementMethod(new TweetMovementMethod());
    }

    public void onTouchLinkify(String text) {
        MainActivity activity = (MainActivity) getContext();

        if (text.startsWith("#") || text.startsWith(FULLWIDTH_NUMBER_SIGN)) {
            activity.getSearchViewManager().search(text);
        } else if (text.startsWith("@") || text.startsWith(FULLWIDTH_COMMERCIAL_AT)){
            User user = null;

            try {
                user = TwitterFactory.getSingleton().showUser(text.substring(1));
            } catch (TwitterException e) {
                e.printStackTrace();
            }

            if (user == null) {
                return;
            }

            TabLayoutManager tm = activity.getTabLayoutManager();
            tm.select(
                tm.addTab(
                    String.format("%s @%s", user.getName(), user.getScreenName()),
                    R.drawable.ic_person,
                    UserFragment.build(user)
                ),
                300
            );
        } else if (URLUtil.isNetworkUrl(text)) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(text));
            activity.startActivity(intent);
        } else {
            Toast.makeText(activity, UNKNOWN_PATTERN_STRING, Toast.LENGTH_LONG).show();
        }
    }

    public class TweetMovementMethod extends LinkMovementMethod {
        @Override
        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                int x = (int) event.getX();
                x -= widget.getTotalPaddingLeft();
                x += widget.getScrollX();

                int y = (int) event.getY();
                y -= widget.getTotalPaddingTop();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int off = layout.getOffsetForHorizontal(layout.getLineForVertical(y), x);
                URLSpan[] links = buffer.getSpans(off, off, URLSpan.class);

                if (links.length != 0) {
                    String url = links[0].getURL();
                    onTouchLinkify(url.trim());

                    return true;
                }
            }

            return super.onTouchEvent(widget, buffer, event);
        }
    }
}
