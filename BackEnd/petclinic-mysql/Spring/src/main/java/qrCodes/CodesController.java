package qrCodes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
class CodesController {

    @Autowired
    CodesRepo qrRepository;

    private final Logger logger = LoggerFactory.getLogger(CodesController.class);

    @RequestMapping(method = RequestMethod.POST, path = "/codes/new")
    public String saveCode(Codes qr) {
        qrRepository.save(qr);
        return "New Code "+ qr.getRandom() + " Saved";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/codes")
    public List<Codes> getAllCodes() {
        logger.info("Entered into Controller Layer");
        List<Codes> results = qrRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/codes/{codeID}")
    public Optional<Codes> findOwnerById(@PathVariable("codeID") int id) {
        logger.info("Entered into Controller Layer");
        Optional<Codes> results = qrRepository.findById(id);
        return results;
    }


}
