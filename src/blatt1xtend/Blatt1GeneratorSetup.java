package blatt1xtend;

import org.eclipse.xtext.ISetup;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class Blatt1GeneratorSetup implements ISetup {

	@Override
	public Injector createInjectorAndDoEMFRegistration() {
        return Guice.createInjector(new Blatt1GeneratorModule());
	}
 

}
