package michael.linker.rewater.data.model.unit;

/**
 * Metric units
 */
public interface IUnit {

    /**
     * Reduces the unit to a compact form.
     *
     * @return compact form of the unit.
     */
    String formatToCompact();
}
