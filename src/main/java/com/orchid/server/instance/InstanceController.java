package com.orchid.server.instance;

import com.orchid.server.domain.Instance;
import java.util.List;

public interface InstanceController {

    public List<Instance> fetchAllInstances();

    public List<Instance> fetchInstancesByDialogFlowRef(String ref);

    public Instance updateInstance(Instance instance) throws Exception;

    public void deleteInstance(Instance instance);
}
