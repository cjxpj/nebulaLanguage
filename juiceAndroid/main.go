package main

import (
	"fyne.io/fyne/v2"
	"fyne.io/fyne/v2/app"
	"fyne.io/fyne/v2/container"
	"fyne.io/fyne/v2/widget"
	"github.com/cjxpj/nebula/juice"
)

func main() {
	go func() {
		juice.Run()
	}()
	myApp := app.New()
	myWindow := myApp.NewWindow("Nebula")

	label := widget.NewLabel("Nebula")
	myWindow.SetContent(container.NewCenter(label))

	myWindow.Resize(fyne.NewSize(400, 200))
	myWindow.ShowAndRun()
}
