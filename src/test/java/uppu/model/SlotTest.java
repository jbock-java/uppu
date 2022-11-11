package uppu.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SlotTest {

    @Test
    void name() {
        Slot slot = Slot.SLOT_1.nextSlot();
        System.out.println(slot);
    }
}