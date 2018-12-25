package com.orchid.server.instance;

import com.orchid.server.domain.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstanceControllerImpl implements InstanceController {

    @Autowired
    InstanceJPAService instanceJPAService;

    @Override
    public List<Instance> fetchAllInstances() {
        return instanceJPAService.fetchAllInstances();
    }

    @Override
    public List<Instance> fetchInstancesByDialogFlowRef(String ref) {
        return instanceJPAService.fetchInstancesByDialogFlowRefLike(ref);
    }

    @Override
    public Instance updateInstance(Instance instance) throws Exception {
        Integer id = instance.getId();
        Instance insByName = instanceJPAService.fetchInstanceByName(instance.getName());
        if (insByName != null && id == null) {
            throw new Exception("Instance with same Instance name already exists");
        }
        Instance insByTitle = instanceJPAService.fetchInstancesByTitle(instance.getTitle());
        if (insByTitle != null && id == null) {
            throw new Exception("Instance with same Instance title already exists");
        }

        return instanceJPAService.storeOrUpdateInstance(instance);
    }

    @Override
    public void deleteInstance(Instance instance) {
        Instance ins = instanceJPAService.fetchInstanceById(instance.getId());
        instanceJPAService.deleteInstance(ins);
    }
}
