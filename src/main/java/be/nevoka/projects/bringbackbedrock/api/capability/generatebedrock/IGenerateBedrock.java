package be.nevoka.projects.bringbackbedrock.api.capability.generatebedrock;

public interface IGenerateBedrock {
    /**
     * Set the status of the bedrock generation layer
     * @param status - Status of the bedrock layers
     */
    void setStatus(byte status);

    /**
     * Get status of the bedrock layer generation
     * @return status of the bedrock layer. true: bedrock layer generated, false: bedrock layer not generated
     */
    boolean getStatus();
}
