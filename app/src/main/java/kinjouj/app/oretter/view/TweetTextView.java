package kinjouj.app.oretter.view;

import java.util.regex.Pattern;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.text.Layout;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.util.Patterns;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;
import com.twitter.Regex;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

import kinjouj.app.oretter.MainActivity;
import kinjouj.app.oretter.R;
import kinjouj.app.oretter.fragments.UserStatusListFragment;
import kinjouj.app.oretter.view.manager.TabLayoutManager;

public class TweetTextView extends TextView {

    private static final Pattern MENTION_PATTERN = Pattern.compile("@[A-Za-z0-9]{1,15}");

    public TweetTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void linkify() {
        Linkify.addLinks(this, MENTION_PATTERN, null);
        Linkify.addLinks(this, Regex.VALID_HASHTAG, null);
        Linkify.addLinks(this, Patterns.WEB_URL, null);
        setMovementMethod(new TweetMovementMethod());
    }

    public void onTouchLinkify(String text) {
        MainActivity activity = (MainActivity)getContext();

        if (text.startsWith("#")) {
            activity.getSearchViewManager().search(text);
        } else if (text.startsWith("@")){
            User user = null;

            try {
                user = TwitterFactory.getSingleton().showUser(text.substring(1));
            } catch (TwitterException e) {
                e.printStackTrace();
            }

            if (user != null) {
                String title = String.format("%s @%s", user.getName(), user.getScreenName());
                UserStatusListFragment fragment = UserStatusListFragment.newInstance(user);
                TabLayoutManager tabManager = activity.getTabLayoutManager();
                TabLayout.Tab tab = tabManager.addTab(title, R.drawable.ic_person, fragment);
                tabManager.select(tab, 300);
            }
        } else {
            Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
        }
    }

    public class TweetMovementMethod extends LinkMovementMethod {

        @Override
        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                int x = (int)event.getX();
                int y = (int)event.getY();
                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();
                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout   = widget.getLayout();
                int line        = layout.getLineForVertical(y);
                int off         = layout.getOffsetForHorizontal(line, x);
                URLSpan[] links = buffer.getSpans(off, off, URLSpan.class);

                if (links.length != 0) {
                    String url = links[0].getURL();
                    onTouchLinkify(url);

                    return true;
                }
            }

            return super.onTouchEvent(widget, buffer, event);
        }
    }
}
