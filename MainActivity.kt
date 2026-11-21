package com.isocraft3d

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import kotlin.math.max

private val Navy = Color(0xFF0B1220)
private val Panel = Color(0xFF151F30)
private val Panel2 = Color(0xFF1D2A3E)
private val Cyan = Color(0xFF42D9F5)
private val Muted = Color(0xFF9AAAC0)
private val White = Color(0xFFEAF2FF)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { IsoCraftApp() }
    }
}

@Composable
fun IsoCraftApp() {
    var page by remember { mutableStateOf("Home") }
    var width by remember { mutableFloatStateOf(60f) }
    var depth by remember { mutableFloatStateOf(40f) }
    var height by remember { mutableFloatStateOf(50f) }
    var lineMode by remember { mutableStateOf("Visible") }
    var wireframe by remember { mutableStateOf(false) }
    var challengeDone by remember { mutableStateOf(false) }

    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Cyan, secondary = Color(0xFF9C8CFF),
            background = Navy, surface = Panel, onSurface = White
        )
    ) {
        Scaffold(
            containerColor = Navy,
            bottomBar = {
                NavigationBar(containerColor = Panel) {
                    listOf("Home", "Workspace", "Challenges", "Classroom", "Pro").forEach { item ->
                        val icon = when(item) {
                            "Home" -> Icons.Default.Home
                            "Workspace" -> Icons.Default.ViewInAr
                            "Challenges" -> Icons.Default.EmojiEvents
                            "Classroom" -> Icons.Default.Groups
                            else -> Icons.Default.Star
                        }
                        NavigationBarItem(
                            selected = page == item,
                            onClick = { page = item },
                            icon = { Icon(icon, null) },
                            label = { Text(item, fontSize = 10.sp) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Cyan, selectedTextColor = Cyan,
                                indicatorColor = Panel2, unselectedIconColor = Muted
                            )
                        )
                    }
                }
            }
        ) { padding ->
            Column(Modifier.fillMaxSize().padding(padding)) {
                when(page) {
                    "Home" -> HomeScreen(onStart = { page = "Workspace" }, onChallenges = { page = "Challenges" })
                    "Workspace" -> WorkspaceScreen(
                        width, depth, height, lineMode, wireframe,
                        { width = it }, { depth = it }, { height = it },
                        { lineMode = it }, { wireframe = it }
                    )
                    "Challenges" -> ChallengeScreen(challengeDone) { challengeDone = !challengeDone }
                    "Classroom" -> ClassroomScreen()
                    else -> ProScreen()
                }
            }
        }
    }
}

@Composable
private fun Header(title: String, subtitle: String) {
    Column(Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 18.dp)) {
        Text("IS O C R A F T   3 D".replace("IS O C R A F T", "ISOCRAFT"), color = Cyan,
            fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
        Spacer(Modifier.height(8.dp))
        Text(title, color = White, fontSize = 27.sp, fontWeight = FontWeight.Bold)
        Text(subtitle, color = Muted, fontSize = 13.sp)
    }
}

@Composable
private fun HomeScreen(onStart: () -> Unit, onChallenges: () -> Unit) {
    LazyColumn(Modifier.fillMaxSize()) {
        item {
            Header("Design in 3D.", "Learn engineering graphics by building and exploring.")
            Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp), colors = CardDefaults.cardColors(containerColor = Panel),
                shape = RoundedCornerShape(22.dp)) {
                Column(Modifier.padding(18.dp)) {
                    Text("YOUR WORKSPACE", color = Cyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(8.dp))
                    Text("From orthographic views to isometric form.", color = White, fontSize = 21.sp, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(14.dp))
                    Button(onClick = onStart, modifier = Modifier.fillMaxWidth()) {
                        Icon(Icons.Default.ViewInAr, null); Spacer(Modifier.width(8.dp)); Text("Open 3D Workspace")
                    }
                }
            }
            Spacer(Modifier.height(14.dp))
            Row(Modifier.padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                StatCard("LEVEL", "01", Modifier.weight(1f))
                StatCard("STREAK", "1 day", Modifier.weight(1f))
                StatCard("SPATIAL IQ", "Building", Modifier.weight(1f))
            }
            Spacer(Modifier.height(18.dp))
            Text("LEARNING PATH", Modifier.padding(horizontal = 20.dp), color = White, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
        }
        items(listOf("01  Simple blocks", "02  Orthographic views", "03  Inclined surfaces", "04  Slots and cuts")) { label ->
            Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp), colors = CardDefaults.cardColors(containerColor = Panel)) {
                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.CheckCircle, null, tint = Cyan)
                    Spacer(Modifier.width(12.dp)); Text(label, color = White)
                }
            }
        }
        item {
            OutlinedButton(onClick = onChallenges, Modifier.fillMaxWidth().padding(16.dp)) {
                Text("Explore challenges")
            }
        }
    }
}

@Composable
private fun StatCard(label: String, value: String, modifier: Modifier) {
    Card(modifier, colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(16.dp)) {
        Column(Modifier.padding(12.dp)) {
            Text(label, color = Muted, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            Text(value, color = White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun WorkspaceScreen(
    w: Float, d: Float, h: Float, mode: String, wire: Boolean,
    onW: (Float)->Unit, onD: (Float)->Unit, onH: (Float)->Unit,
    onMode: (String)->Unit, onWire: (Boolean)->Unit
) {
    Column(Modifier.fillMaxSize()) {
        Header("Workspace", "Adjust dimensions and inspect linked projections.")
        Row(Modifier.padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(selected = mode == "Visible", onClick = { onMode("Visible") }, label = { Text("Visible") })
            FilterChip(selected = mode == "Hidden", onClick = { onMode("Hidden") }, label = { Text("Hidden") })
            FilterChip(selected = mode == "Center", onClick = { onMode("Center") }, label = { Text("Centerline") })
        }
        Row(Modifier.weight(1f).padding(horizontal = 12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Column(Modifier.weight(1f)) {
                Text("2D ORTHOGRAPHIC", color = Cyan, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                ProjectionPanel(w, d, h, mode)
            }
            Column(Modifier.weight(1.15f)) {
                Text("3D ISOMETRIC", color = Cyan, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                IsometricPanel(w, d, h, wire)
            }
        }
        Card(Modifier.fillMaxWidth().padding(12.dp), colors = CardDefaults.cardColors(containerColor = Panel)) {
            Column(Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("MODEL DIMENSIONS", color = White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Spacer(Modifier.weight(1f))
                    Text("Units", color = Muted, fontSize = 11.sp)
                }
                DimensionSlider("Width", w, onW)
                DimensionSlider("Depth", d, onD)
                DimensionSlider("Height", h, onH)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Wireframe view", color = Muted, fontSize = 12.sp)
                    Spacer(Modifier.weight(1f))
                    Switch(checked = wire, onCheckedChange = onWire)
                }
            }
        }
    }
}

@Composable
private fun DimensionSlider(label: String, value: Float, onChange: (Float)->Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(label, color = Muted, fontSize = 11.sp, modifier = Modifier.width(55.dp))
        Slider(value = value, onValueChange = onChange, valueRange = 10f..100f, modifier = Modifier.weight(1f))
        Text(value.toInt().toString(), color = White, fontSize = 11.sp, modifier = Modifier.width(30.dp))
    }
}

@Composable
private fun ProjectionPanel(w: Float, d: Float, h: Float, mode: String) {
    Card(Modifier.fillMaxSize().padding(top = 6.dp), colors = CardDefaults.cardColors(containerColor = Panel),
        shape = RoundedCornerShape(16.dp)) {
        Canvas(Modifier.fillMaxSize().padding(6.dp)) {
            drawGrid(size.width, size.height)
            val s = max(0.8f, minOf(size.width / 135f, size.height / 190f))
            val c = Color(0xFF59DDF5)
            fun rect(x: Float, y: Float, rw: Float, rh: Float, dashed: Boolean = false) {
                drawRect(c, Offset(x,y), androidx.compose.ui.geometry.Size(rw,rh),
                    style = if (dashed) Stroke(1.5f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f,4f))) else Stroke(2f))
            }
            val left = size.width * .12f
            val top = size.height * .14f
            val gap = 18f
            rect(left, top + h*s + gap, w*s, h*s, mode == "Hidden")
            rect(left, top, w*s, d*s, mode == "Hidden")
            rect(left + w*s + gap, top + d*s + gap, d*s, h*s, mode == "Hidden")
            drawLine(c.copy(alpha=.45f), Offset(size.width*.06f, size.height*.5f), Offset(size.width*.94f, size.height*.5f), 1f)
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawGrid(w: Float, h: Float) {
    val step = 18f
    for (x in 0..(w/step).toInt()) drawLine(Color(0xFF26364B), Offset(x*step,0f), Offset(x*step,h), .6f)
    for (y in 0..(h/step).toInt()) drawLine(Color(0xFF26364B), Offset(0f,y*step), Offset(w,y*step), .6f)
}

@Composable
private fun IsometricPanel(w: Float, d: Float, h: Float, wire: Boolean) {
    Card(Modifier.fillMaxSize().padding(top = 6.dp), colors = CardDefaults.cardColors(containerColor = Panel),
        shape = RoundedCornerShape(16.dp)) {
        Canvas(Modifier.fillMaxSize().padding(8.dp)) {
            drawGrid(size.width, size.height)
            val scale = minOf(size.width/(w+d+20f), size.height/(w*.5f+d*.5f+h+20f)) * .78f
            val origin = ProjectionEngine.Point2(size.width*.5f, size.height*.62f)
            val vertices = ProjectionEngine.boxVertices(ProjectionEngine.Box3(w,d,h))
                .map { ProjectionEngine.isometric(it, scale, origin) }
            val fills = listOf(
                0 to listOf(0,1,5,4),
                1 to listOf(1,2,6,5),
                2 to listOf(4,5,6,7)
            )
            val faceColors = listOf(Color(0xFF236C89), Color(0xFF17445E), Color(0xFF3198B8))
            fills.forEachIndexed { index, face ->
                val path = Path().apply {
                    moveTo(vertices[face[0]].x, vertices[face[0]].y)
                    face.drop(1).forEach { lineTo(vertices[it].x, vertices[it].y) }
                    close()
                }
                if (!wire) drawPath(path, faceColors[index].copy(alpha=.85f))
                drawPath(path, Cyan, style = Stroke(2f))
            }
            ProjectionEngine.edges.forEach { (a,b) ->
                drawLine(Cyan, Offset(vertices[a].x,vertices[a].y), Offset(vertices[b].x,vertices[b].y),
                    1.7f, pathEffect = if (wire && (a+b)%3==0) PathEffect.dashPathEffect(floatArrayOf(5f,4f)) else null)
            }
        }
    }
}

@Composable
private fun ChallengeScreen(done: Boolean, onToggle: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Header("Challenge hub", "Practice spatial reasoning step by step.")
        Card(Modifier.fillMaxWidth().padding(horizontal = 16.dp), colors = CardDefaults.cardColors(containerColor = Panel)) {
            Column(Modifier.padding(18.dp)) {
                Text("BEGINNER • LEVEL 01", color = Cyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Text("The basic cuboid", color = White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text("Identify the front, top and side views of a rectangular block.", color = Muted)
                Spacer(Modifier.height(14.dp))
                Text(if(done) "Completed locally ✓" else "Goal: inspect all three orthographic views", color = White)
                Spacer(Modifier.height(10.dp))
                Button(onClick = onToggle, Modifier.fillMaxWidth()) { Text(if(done) "Reset challenge" else "Mark challenge complete") }
            }
        }
        Spacer(Modifier.height(16.dp))
        Text("NEXT TOPICS", Modifier.padding(horizontal = 20.dp), color = White, fontWeight = FontWeight.Bold)
        listOf("Inclined plane", "Rectangular slot", "Cylindrical hole", "Stepped block").forEachIndexed { i, item ->
            ListItem(headlineContent = { Text(item, color = White) },
                supportingContent = { Text("Progressive engineering graphics exercise", color = Muted) },
                leadingContent = { Icon(Icons.Default.LockOpen, null, tint = Cyan) },
                colors = ListItemDefaults.colors(containerColor = Navy))
        }
    }
}

@Composable
private fun ClassroomScreen() {
    Column(Modifier.fillMaxSize()) {
        Header("Classroom", "A simple classroom concept for instructors and learners.")
        Card(Modifier.fillMaxWidth().padding(16.dp), colors = CardDefaults.cardColors(containerColor = Panel)) {
            Column(Modifier.padding(18.dp)) {
                Icon(Icons.Default.Groups, null, tint = Cyan, modifier = Modifier.size(36.dp))
                Spacer(Modifier.height(10.dp))
                Text("Join a learning group", color = White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text("Class-code sharing and live instructor dashboards require a connected backend. This prototype keeps the classroom screen local.", color = Muted)
                Spacer(Modifier.height(12.dp))
                var code by remember { mutableStateOf("") }
                OutlinedTextField(value = code, onValueChange = { code = it.filter(Char::isDigit).take(6) },
                    label = { Text("Enter 6-digit class code") }, singleLine = true)
                Spacer(Modifier.height(8.dp))
                Button(onClick = { }, enabled = code.length == 6) { Text("Continue") }
            }
        }
    }
}

@Composable
private fun ProScreen() {
    Column(Modifier.fillMaxSize()) {
        Header("Pro Engineer", "Explore the planned professional toolkit.")
        listOf("Advanced solids and angled features", "STL / DXF import and export", "AI drawing tutor", "AR model verification", "Instructor analytics").forEach {
            ListItem(headlineContent = { Text(it, color = White) },
                leadingContent = { Icon(Icons.Default.Star, null, tint = Cyan) },
                colors = ListItemDefaults.colors(containerColor = Navy))
        }
        Text("Subscription purchase is not enabled in this prototype. Google Play Billing must be configured and tested with a Play Console account before release.",
            color = Muted, modifier = Modifier.padding(20.dp), fontSize = 12.sp)
    }
}
