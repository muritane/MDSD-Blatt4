package blatt1xtend;
import org.eclipse.xtext.resource.generic.AbstractGenericResourceSupport;

import com.google.inject.Module;
 
public class Blatt1GeneratorSupport extends AbstractGenericResourceSupport {
	@Override
    protected Module createGuiceModule() {
        return new Blatt1GeneratorModule();
    }
 
}
