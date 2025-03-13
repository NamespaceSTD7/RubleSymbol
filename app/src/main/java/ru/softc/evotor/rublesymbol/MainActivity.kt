package ru.softc.evotor.rublesymbol

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.evotor.devices.commons.Constants
import ru.evotor.devices.commons.DeviceServiceConnector
import ru.evotor.devices.commons.printer.PrinterDocument
import ru.evotor.devices.commons.printer.printable.IPrintable
import ru.evotor.devices.commons.printer.printable.PrintableText
import ru.softc.evotor.rublesymbol.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                printDocument(generatePrintDocument())
            }
        }
    }

    private fun generatePrintDocument(): PrinterDocument {
        val printables = arrayOf<IPrintable>(PrintableText("0.00₽"))
        return PrinterDocument(*printables)
    }

    private fun printDocument(document: PrinterDocument) {
        DeviceServiceConnector.startInitConnections(this)

        val printerService = DeviceServiceConnector.getPrinterService()

        printerService.printDocument(Constants.DEFAULT_DEVICE_INDEX, document)
    }
}