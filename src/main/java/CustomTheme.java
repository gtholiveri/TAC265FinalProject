import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;

/**
 * Was going to try to use this but it's tricky to get to do what you want so I just used a default one
 */
public class CustomTheme extends SimpleTheme {
    /**
     * Creates a new {@link SimpleTheme} object that uses the supplied constructor arguments as the default style
     *
     * @param foreground Color to use as the foreground unless overridden
     * @param background Color to use as the background unless overridden
     * @param styles     Extra SGR styles to apply unless overridden
     */
    public CustomTheme(TextColor foreground, TextColor background, SGR... styles) {
        super(foreground, background, styles);
    }
}
