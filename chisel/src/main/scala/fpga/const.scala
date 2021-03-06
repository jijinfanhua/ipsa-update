package fpga

import chisel3._
import chisel3.util._

object const {
    val processor_number = 16
    val config_number = 2
    val max_header_number = 16
    val bits_per_cycle = 512 // max input bits per cycle
    val processor_id_width = log2Ceil(processor_number)
    val config_id_width = log2Ceil(config_number)

    object PHV {
        val total_data_length  = 128
        val header_data_length = bits_per_cycle / 8
        val offset_width = log2Ceil(total_data_length)

        val transition_field_width = 16
    }

    object PAR /* parser */ {
        val stage_number = 4
        val stage_id_width = log2Ceil(stage_number)
        
        val state_number = stage_number * processor_number
        val state_id_width = log2Ceil(state_number)
    }

    object GTW /* gateway */ {
        val lut_capacity = 16
        val lut_entry_id_width = log2Ceil(lut_capacity)
    }

    object PROC /* processor */ {
        val cycle_number = 32
        val cycle_id_width = log2Ceil(cycle_number)
    }

    object FBUF /* front buffer */ {
        val capacity = processor_number + PROC.cycle_number
        val width = PHV.header_data_length * 8
    }

    object PDQ /* packet data queue */ {
        val capacity = processor_number * PROC.cycle_number + FBUF.capacity
    }

    object CTRL /* controller */ {
        val update_item_capacity = PDQ.capacity
        val update_opcode_width = 4
        val update_parameter_width = 12
        val max_suspend_time = FBUF.capacity
        val suspend_time_width = log2Ceil(max_suspend_time)
        val update_item_width = update_opcode_width + update_parameter_width
    }

    object PCIE {
        val addr_width = 20
        val data_width = 64
    }

    object STST /* set stage */ {
        val capacity = 256 // TODO: should be set to the limit of stage-updates
        val total_width = 64
    }
}