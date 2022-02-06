package pt.tml.plannedoffer.controllers;

import com.google.common.reflect.ClassPath;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.powerqubit.validator.core.annotation.GtfsValidator;
import pt.powerqubit.validator.core.validator.FileValidator;
import pt.powerqubit.validator.core.validator.SingleEntityValidator;
import pt.tml.plannedoffer.export.writer.CsvPgCopyWriter;
import pt.tml.plannedoffer.global.ApplicationState;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


@RestController
@CrossOrigin("*")
public class StatusController
{

    @Autowired
    CsvPgCopyWriter csvWriter;
//
//    @Autowired
//    SolaceSenderService solaceSenderService;


    @GetMapping("status-report")
    public ServiceStatus getServiceStatus() throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IOException, InterruptedException, URISyntaxException, ClassNotFoundException
    {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(true);

        var loader = Thread.currentThread().getContextClassLoader();

        scanner.addIncludeFilter(new AnnotationTypeFilter(GtfsValidator.class));
        for (BeanDefinition bd : scanner.findCandidateComponents("pt.powerqubit.validator.core.validator"))
        {
            System.out.println(bd.getBeanClassName());
            var clazz = loader.loadClass(bd.getBeanClassName());
        }







      //  var loader = Thread.currentThread().getContextClassLoader();


        var classPath = ClassPath.from(ClassLoader.getSystemClassLoader());

        var top = classPath.getTopLevelClassesRecursive("pt.powerqubit.validator.core.validator");

        var ccp= System.getProperty("java.class.path");

        var resolver = new PathMatchingResourcePatternResolver(this.getClass().getClassLoader());

        var resources= resolver.getResources("pt/powerqubit/validator/core/validator/*.class");


//
//
//        for (Resource resource : resources)
//        {
//
//
//
//
//
//
//
//
//
//
//            var url = resource.getURL();
//            URL[] urls = new URL[]{url};
//
//            //load this folder into Class loader
//            ClassLoader cl = URLClassLoader.newInstance(urls);
//
//            var res2= cl.getResources("*");
//
//
//            var clayy= cl.loadClass("55GtfsAttributionAgencyIdForeignKeyValidator");
//
//            if (clayy.isAnnotationPresent(GtfsValidator.class))
//            {
//                if (SingleEntityValidator.class.isAssignableFrom(clayy))
//                {
//                    System.out.println("cally_single");
//                }
//                else if (FileValidator.class.isAssignableFrom(clayy))
//                {
//                    System.out.println("calyy_file");
//                }
//            }
//        }


        for (ClassPath.ClassInfo classInfo : top)
        {
            Class<?> clazz = classInfo.load();
            if (clazz.isAnnotationPresent(GtfsValidator.class))
            {
                if (SingleEntityValidator.class.isAssignableFrom(clazz))
                {
                    System.out.println("single");
                }
                else if (FileValidator.class.isAssignableFrom(clazz))
                {
                    System.out.println("file");
                }
            }
        }


//        SolaceEvent event = new SolaceEvent("Progress", "Message", false);
//
//        solaceSenderService.SendToSolace(event);

        ServiceStatus stats = new ServiceStatus();
        stats.setHeapSize(Runtime.getRuntime().totalMemory());
        stats.setHeapSizeMax(Runtime.getRuntime().maxMemory());
        stats.setHeapFreeSize(Runtime.getRuntime().freeMemory());
        stats.setPersisting(ApplicationState.entityPersistenceBusy);
        stats.setUploading(ApplicationState.uploadBusy);
        stats.setValidating(ApplicationState.validationBusy);
        stats.setExporting(ApplicationState.exportBusy);
        return stats;
    }

}
